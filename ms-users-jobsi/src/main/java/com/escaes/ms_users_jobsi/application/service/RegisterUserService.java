package com.escaes.ms_users_jobsi.application.service;

import java.util.UUID;

import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.domain.exception.UserAlreadyExistsException;
import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;
import com.escaes.ms_users_jobsi.domain.model.User;
import com.escaes.ms_users_jobsi.domain.port.in.RegisterUserUseCase;
import com.escaes.ms_users_jobsi.domain.port.out.UserRepositoryPort;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    public Mono<UUID> registerUser(RegisterUserCommand command) {
             return userRepository.existsByEmail(command.getEmail())
             .flatMap(exists->{
                if (exists) {
                    return Mono.error(new UserAlreadyExistsException("Email already in use"));
                } 
                 User newUser = User.builder()
                     .email(command.getEmail())
                     .password(command.getPassword())
                     .firstName(command.getFirstName())
                     .lastName(command.getLastName())
                     .documentNumber(command.getDocumentNumber())
                     .role(Role.USER)
                     .gender(command.getGender() != null ? command.getGender() : Gender.OTHER)
                     .build();

                return userRepository.save(newUser)
                        .map(User::getId);

             });
    }
        
}
