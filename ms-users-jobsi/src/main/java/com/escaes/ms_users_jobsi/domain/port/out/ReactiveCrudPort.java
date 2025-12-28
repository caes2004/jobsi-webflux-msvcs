package com.escaes.ms_users_jobsi.domain.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCrudPort<T, ID> {

    Mono<T> update(T entity);

    Mono<T> findById(ID id);

    Mono<Boolean> existsById(ID id);

    Mono<Void> deleteById(ID id);

    Flux<T> findAll();

}
