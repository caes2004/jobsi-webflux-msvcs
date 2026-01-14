package com.escaes.ms_users_jobsi.adapter.in.rest.controller;

import com.escaes.ms_users_jobsi.application.dto.AuthResponse;
import com.escaes.ms_users_jobsi.application.dto.LoginCommand;
import com.escaes.ms_users_jobsi.application.service.AuthenticateUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name="Authentication", description="Related operations with authentication")
public class AuthController {

    private final AuthenticateUserService authenticateUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public Mono<AuthResponse> authenticate(@Valid @RequestBody LoginCommand command) {
        LOGGER.info("Received login command with email= {}", command.getEmail());

        Mono<AuthResponse> response= authenticateUserService.authenticate(command);

        LOGGER.info("Auth response successful from authenticateUserService: {}", response);
        return response;
    }
}
