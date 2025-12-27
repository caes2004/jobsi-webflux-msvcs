package com.escaes.ms_users_jobsi.domain.port.in;

import java.util.UUID;

import com.escaes.ms_users_jobsi.application.dto.UserDto;

import reactor.core.publisher.Mono;

public interface GetUserUseCase {

    Mono<UserDto> getById(UUID id);

    Mono<UserDto> getByEmail(String email);

    Mono<UserDto> getByDocumentNumber(String documentNumber);

}
