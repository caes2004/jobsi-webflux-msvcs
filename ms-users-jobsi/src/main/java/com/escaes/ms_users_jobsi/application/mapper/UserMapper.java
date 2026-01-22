package com.escaes.ms_users_jobsi.application.mapper;


import java.time.LocalDate;

import java.util.UUID;

import com.escaes.ms_users_jobsi.adapter.out.persistence.entity.UserEntity;
import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;
import com.escaes.ms_users_jobsi.domain.model.User;

/**
 * UUID Mapping Strategy
 * <p>
 * In this project, the user identifier is represented differently across layers
 * by design:
 * <p>
 * - Persistence layer (R2DBC / MySQL):
 * The ID is stored and mapped as a String (VARCHAR).
 * This is required because R2DBC MySQL does not reliably support
 * encoding/decoding java.util.UUID natively.
 * <p>
 * - Domain layer:
 * The ID is represented as java.util.UUID.
 * This preserves strong typing, domain correctness, and prevents
 * accidental misuse of identifiers.
 * <p>
 * Mapping responsibility:
 * - Entity → Domain:
 * String → UUID using UUID.fromString(...)
 * <p>
 * - Domain → Entity:
 * UUID → String using UUID.toString()
 * <p>
 * This conversion is intentionally centralized in the mapper to:
 * - Keep infrastructure constraints out of the domain
 * - Avoid leaking database-specific limitations into business logic
 * - Ensure a single, consistent conversion point for identifiers
 * <p>
 * IMPORTANT:
 * - Services and use cases must work ONLY with UUID (domain type)
 * - Persistence adapters must work ONLY with String IDs
 */
public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(RegisterUserCommand cmd, String encodedPassword) {
        UUID id = UUID.randomUUID();

        return User.builder()
                .id(id)
                .documentNumber(cmd.getDocumentNumber())
                .name(cmd.getName())
                .firstName(cmd.getFirstName())
                .lastName(cmd.getLastName())
                .email(cmd.getEmail())
                .password(encodedPassword)
                .phoneNumber(cmd.getPhoneNumber())
                .birthDate(cmd.getBirthDate())
                .isActive(true)
                .role(cmd.getRole() != null ? cmd.getRole() : Role.USER)
                .gender(cmd.getGender() != null ? cmd.getGender() : Gender.OTHER)
                .build();

    }

    public static User EntityToDomain(UserEntity entity) {
        if (entity == null) return null;

        return User.builder(
                ).id(UUID.fromString(entity.getId()))
                .documentNumber(entity.getDocumentNumber())
                .name(entity.getName())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .phoneNumber(entity.getPhoneNumber())
                .birthDate(entity.getBirthDate())
                .isActive(entity.isActive())
                .role(entity.getRole() != null ? Role.valueOf(entity.getRole()) : Role.USER)
                .gender(entity.getGender() != null ? Gender.valueOf(entity.getGender()) : Gender.OTHER)
                .build();

    }

    public static UserDto toDto(User user) {
        if (user == null) return null;
        LocalDate birth = user.getBirthDate();
        return UserDto.builder()
                .id(user.getId())
                .documentNumber(user.getDocumentNumber())
                .name(user.getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(birth)
                .isActive(user.isActive())
                .role(user.getRole())
                .gender(user.getGender())
                .build();
    }

    public static User updateFromCommand(User existing, UpdateUserCommand cmd) {
        if (existing == null) return null;
        String name = cmd.getName() != null ? cmd.getName() : existing.getName();
        String firstName = cmd.getFirstName() != null ? cmd.getFirstName() : existing.getFirstName();
        String lastName = cmd.getLastName() != null ? cmd.getLastName() : existing.getLastName();
        String phone = cmd.getPhoneNumber() != null ? cmd.getPhoneNumber() : existing.getPhoneNumber();
        LocalDate birth = cmd.getBirthDate() != null ? (cmd.getBirthDate()) : existing.getBirthDate();
        boolean active = cmd.getIsActive() != null ? cmd.getIsActive() : existing.isActive();
        Role role = cmd.getRole() != null ? cmd.getRole() : existing.getRole();
        Gender gender = cmd.getGender() != null ? cmd.getGender() : existing.getGender();

        return User.builder()
                .id(existing.getId())
                .documentNumber(existing.getDocumentNumber())
                .name(name)
                .firstName(firstName)
                .lastName(lastName)
                .email(existing.getEmail())
                .password(existing.getPassword())
                .phoneNumber(phone)
                .birthDate(birth)
                .isActive(active)
                .role(role)
                .gender(gender)
                .build();

    }


    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        return UserEntity.builder()
                .id(user.getId().toString())
                .documentNumber(user.getDocumentNumber())
                .name(user.getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate() != null ? user.getBirthDate() : null)
                .isActive(user.isActive())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .gender(user.getGender() != null ? user.getGender().name() : Gender.OTHER.toString())
                .build();
    }
}