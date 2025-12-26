package com.escaes.ms_users_jobsi.domain.port.in;

import java.util.UUID;

import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;

import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {

    Mono<UserDto> update(UUID id, UpdateUserCommand command);

}
