package com.escaes.ms_users_jobsi.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.escaes.ms_users_jobsi.application.dto.AuthResponse;
import com.escaes.ms_users_jobsi.application.dto.LoginCommand;
import com.escaes.ms_users_jobsi.adapter.out.security.JWTutil;
import com.escaes.ms_users_jobsi.domain.exception.InvalidCredentialsException;
import com.escaes.ms_users_jobsi.domain.port.in.AuthenticateUserUseCase;
import com.escaes.ms_users_jobsi.domain.port.out.UserRepositoryPort;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class AuthenticateUserService implements AuthenticateUserUseCase{

    private final UserRepositoryPort userRepository;
    private final JWTutil jwtutil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<AuthResponse> authenticate(LoginCommand command) {
        return userRepository.findByEmail(command.getEmail())
            .flatMap(user -> {
                if (passwordEncoder.matches(command.getPassword(), user.getPassword())) {
                    String role = user.getRole() != null ? user.getRole().name() : "USER";
                    String token = jwtutil.generateToken(user.getEmail(), role);
                    return Mono.just(AuthResponse.builder().token(token).build());
                }
                return Mono.error(new InvalidCredentialsException("Invalid credentials"));
            })
            .switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid credentials")));
    }
}