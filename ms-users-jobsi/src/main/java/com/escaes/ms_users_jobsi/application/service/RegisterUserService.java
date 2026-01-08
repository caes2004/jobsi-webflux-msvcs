package com.escaes.ms_users_jobsi.application.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.domain.exception.UserAlreadyExistsException;
import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;
import com.escaes.ms_users_jobsi.domain.model.User;
import com.escaes.ms_users_jobsi.domain.port.in.RegisterUserUseCase;
import com.escaes.ms_users_jobsi.domain.port.out.UserRepositoryPort;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UUID> registerUser(RegisterUserCommand command) {
        return userRepository.existsByEmail(command.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException("Email already in use"));
                    }
                    String encodedPassword = passwordEncoder.encode(command.getPassword());
                    User newUser = User.builder()
                            .id(UUID.randomUUID())
                            .email(command.getEmail())
                            .password(encodedPassword)
                            .name(command.getName())
                            .firstName(command.getFirstName())
                            .lastName(command.getLastName())
                            .documentNumber(command.getDocumentNumber())
                            .phoneNumber(command.getPhoneNumber())
                            .birthDate(command.getBirthDate())
                            .role(Role.USER)
                            .gender(command.getGender() != null ? command.getGender() : Gender.OTHER)
                            .isActive(Boolean.TRUE)
                            .build();

                    return userRepository.create(newUser)
                            .map(User::getId);

                });
    }

}
