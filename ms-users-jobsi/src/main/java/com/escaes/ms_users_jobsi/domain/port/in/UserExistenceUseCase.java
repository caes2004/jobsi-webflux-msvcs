package com.escaes.ms_users_jobsi.domain.port.in;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface UserExistenceUseCase {

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsById(UUID id);

    Mono<Boolean> existsByDocumentNumber(String documentNumber);

}
