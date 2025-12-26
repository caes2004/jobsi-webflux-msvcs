package com.escaes.ms_users_jobsi.domain.port.in;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface DeleteUserUseCase {

    Mono<Void> deleteById(UUID id);

    Mono<Void> deleteByEmail(String email);

    Mono<Void> deleteByDocumentNumber(String documentNumber);

}
