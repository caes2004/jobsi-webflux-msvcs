package com.escaes.ms_users_jobsi.domain.port.in;

import com.escaes.ms_users_jobsi.application.dto.AuthResponse;
import com.escaes.ms_users_jobsi.application.dto.LoginCommand;

import reactor.core.publisher.Mono;

public interface AuthenticateUserUseCase {

     Mono<AuthResponse> authenticate(LoginCommand command);

}
