package com.escaes.ms_users_jobsi.domain.port.in;

import com.escaes.ms_users_jobsi.application.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ListUsersUseCase {

    Flux<UserDto> listAll(int page, int size);

    Flux<UserDto> listByRole(String role,  int page, int size);

    Flux<UserDto> listByIsActive(boolean isActive, int page, int size);

    Flux<UserDto> listByGender(String gender, int page, int size);

    Mono<Integer> countUsers();

}
