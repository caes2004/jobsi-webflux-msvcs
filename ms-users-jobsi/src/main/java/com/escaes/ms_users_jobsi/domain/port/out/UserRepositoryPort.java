package com.escaes.ms_users_jobsi.domain.port.out;

import java.util.UUID;

import com.escaes.ms_users_jobsi.domain.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort extends ReactiveCrudPort<User, UUID> {

    Mono<User>create(User entity);

    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByDocumentNumber(String documentNumber);

    Mono<User> findByEmail(String email);
    Mono<User> findByDocumentNumber(String documentNumber);

    Mono<Void> deleteByDocumentNumber(String documentNumber);
    Mono<Void> deleteByEmail(String email);

    Flux<User> findAllByRole(String role, int page, int size);
    Flux<User> findAllByIsActive(boolean isActive, int page, int size);
    Flux<User> findAllByGender(String gender, int page, int size);
    Mono<Integer> countUsers();
}
