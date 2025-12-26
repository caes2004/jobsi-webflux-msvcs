package com.escaes.ms_users_jobsi.adapter.out.persistence.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.escaes.ms_users_jobsi.adapter.out.persistence.entity.UserEntity;

import reactor.core.publisher.Mono;

public interface R2dbcUserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByDocumentNumber(String documentNumber);

    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findByDocumentNumber(String documentNumber);

    Mono<Void> deleteByDocumentNumber(String documentNumber);

}
