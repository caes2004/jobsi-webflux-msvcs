package com.escaes.ms_users_jobsi.domain.port.in;

import java.util.UUID;

import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;

import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {

    Mono<UUID> registerUser(RegisterUserCommand command);




}
