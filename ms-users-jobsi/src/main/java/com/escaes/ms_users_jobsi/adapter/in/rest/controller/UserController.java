package com.escaes.ms_users_jobsi.adapter.in.rest.controller;

import com.escaes.ms_users_jobsi.adapter.out.security.AuthUser;
import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import com.escaes.ms_users_jobsi.application.service.CrudUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Related operations with users")
public class UserController {

    private final CrudUserService crudUserService;

    @GetMapping("/me")
    public Mono<UserDto> getMyProfile() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AuthUser) ctx.getAuthentication().getPrincipal())
                .map(AuthUser::getId)
                .flatMap(crudUserService::getById);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<UserDto> updateMyProfile(UpdateUserCommand command) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AuthUser) ctx.getAuthentication().getPrincipal())
                .map(AuthUser::getId)
                .flatMap(userId -> crudUserService.update(userId, command));
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUserById(@PathVariable String id) {
        return crudUserService.getById(UUID.fromString(id));
    }

    @GetMapping
    public Flux<UserDto> getUsers(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return crudUserService.findUsersCriteria(gender, role, active, page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteUser(@PathVariable String id) {
        return crudUserService.deleteById(UUID.fromString(id));
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AuthUser) ctx.getAuthentication().getPrincipal())
                .map(AuthUser::getId)
                .flatMap(crudUserService::deleteById);
    }
}
