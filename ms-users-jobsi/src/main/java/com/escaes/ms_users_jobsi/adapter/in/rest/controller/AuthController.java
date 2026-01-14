package com.escaes.ms_users_jobsi.adapter.in.rest.controller;

import com.escaes.ms_users_jobsi.application.dto.AuthResponse;
import com.escaes.ms_users_jobsi.application.dto.LoginCommand;
import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.application.service.AuthenticateUserService;
import com.escaes.ms_users_jobsi.application.service.RegisterUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Related operations with authentication")
public class AuthController {

    private final AuthenticateUserService authenticateUserService;

    private final RegisterUserService  registerUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public Mono<AuthResponse> authenticate(@Valid @RequestBody LoginCommand command) {
        LOGGER.info("Received login command with email= {}", command.getEmail());

        return authenticateUserService.authenticate(command).log("AUTH_FLOW");
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UUID> registerUser(@Valid @RequestBody RegisterUserCommand command) {
        LOGGER.info("Received register command with email= {}", command.getEmail());

        return registerUserService.registerUser(command).log("REGISTER_FLOW");
    }
}
