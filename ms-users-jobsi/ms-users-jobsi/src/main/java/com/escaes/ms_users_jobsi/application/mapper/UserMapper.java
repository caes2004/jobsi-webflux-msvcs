package com.escaes.ms_users_jobsi.application.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import com.escaes.ms_users_jobsi.adapter.out.persistence.entity.UserEntity;
import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;
import com.escaes.ms_users_jobsi.domain.model.User;

public final class UserMapper {

    private UserMapper() {}

    public static User toDomain(RegisterUserCommand cmd) {
        UUID id = UUID.randomUUID();
        Date birth = toDate(cmd.getBirthDate());
        return new User(
            id,
            cmd.getDocumentNumber(),
            cmd.getName(),
            cmd.getFirstName(),
            cmd.getLastName(),
            cmd.getEmail(),
            cmd.getPassword(),
            cmd.getPhoneNumber(),
            birth,
            true,
            Role.USER,
            cmd.getGender()
        );
    }
    public static User EntityToDomain(UserEntity entity) {
        if (entity == null) return null;
        Date birth = toDate(entity.getBirthDate() != null ? LocalDate.parse(entity.getBirthDate()) : null);
        return new User(
            entity.getId(),
            entity.getDocumentNumber(),
            entity.getName(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getPhoneNumber(),
            birth,
            entity.isActive(),
            entity.getRole() != null ? Role.valueOf(entity.getRole()) : null,
            entity.getGender() != null ? Gender.valueOf(entity.getGender()) : null
        );
    }

    public static UserDto toDto(User user) {
        if (user == null) return null;
        Date birth = user.getBirthDate();
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
        Date birth = cmd.getBirthDate() != null ? toDate(cmd.getBirthDate()) : existing.getBirthDate();
        boolean active = cmd.getIsActive() != null ? cmd.getIsActive() : existing.isActive();
        Role role = cmd.getRole() != null ? cmd.getRole() : existing.getRole();
        Gender gender = cmd.getGender() != null ? cmd.getGender() : existing.getGender();

        return new User(
            existing.getId(),
            existing.getDocumentNumber(),
            name,
            firstName,
            lastName,
            existing.getEmail(),
            existing.getPassword(),
            phone,
            birth,
            active,
            role,
            gender
        );
    }

    private static Date toDate(LocalDate ld) {
        if (ld == null) return null;
        Instant instant = ld.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        return UserEntity.builder()
            .id(user.getId())
            .documentNumber(user.getDocumentNumber())
            .name(user.getName())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .password(user.getPassword())
            .phoneNumber(user.getPhoneNumber())
            .birthDate(user.getBirthDate() != null ? user.getBirthDate().toString() : null)
            .isActive(user.isActive())
            .role(user.getRole() != null ? user.getRole().name() : null)
            .gender(user.getGender()!= null ?  user.getGender().name() : Gender.OTHER.toString())
            .build();
    }
}