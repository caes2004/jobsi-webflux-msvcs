package com.escaes.ms_users_jobsi.adapter.in.rest.controller;

import com.escaes.ms_users_jobsi.adapter.out.security.JWTutil;
import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Related operations with users")
public class UserController {

    private final JWTutil  jwtutil;

    @GetMapping("/me")
    public Mono<UserDto> getMyProfile() {
        return Mono.just(UserDto.builder().build());
    }

    @PutMapping("/me")
    public Mono<UserDto> updateMyProfile(UpdateUserCommand command) {
        return Mono.just(UserDto.builder().build());
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUserById(@PathVariable String id) {
        return Mono.just(UserDto.builder().build());
    }

    @GetMapping
    public Flux<UserDto> getAllUsers() {
        return Flux.just(UserDto.builder().build());
    }
}
