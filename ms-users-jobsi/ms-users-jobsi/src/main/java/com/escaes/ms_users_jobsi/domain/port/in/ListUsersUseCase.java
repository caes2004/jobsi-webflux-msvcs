package com.escaes.ms_users_jobsi.domain.port.in;

import com.escaes.ms_users_jobsi.application.dto.UserDto;

import reactor.core.publisher.Flux;

public interface ListUsersUseCase {

    Flux<UserDto> listAll();

    Flux<UserDto> listByRole(String role);

    Flux<UserDto> listByIsActive(boolean isActive);

    Flux<UserDto> listByGender(String gender);

}
