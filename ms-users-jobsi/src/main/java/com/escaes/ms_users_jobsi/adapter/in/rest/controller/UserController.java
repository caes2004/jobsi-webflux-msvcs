package com.escaes.ms_users_jobsi.adapter.in.rest.controller;

import com.escaes.ms_users_jobsi.adapter.out.security.AuthUser;
import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import com.escaes.ms_users_jobsi.application.service.CrudUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")//All endpoints require JWT
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations related to user management. All endpoints require authentication.")
public class UserController {

    private final CrudUserService crudUserService;


    // ============================
    // GET /users/me
    // ============================

    @Operation(
            summary = "Get authenticated user's profile",
            description = "Returns the profile information of the currently authenticated user"
    )
    @GetMapping("/me")
    public Mono<UserDto> getMyProfile() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AuthUser) ctx.getAuthentication().getPrincipal())
                .map(AuthUser::getId)
                .flatMap(crudUserService::getById);
    }

    // ============================
    // PUT /users/me
    // ============================

    @Operation(
            summary = "Update authenticated user's profile",
            description = "Updates the allowed profile fields of the currently authenticated user"
    )
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<UserDto> updateMyProfile(UpdateUserCommand command) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AuthUser) ctx.getAuthentication().getPrincipal())
                .map(AuthUser::getId)
                .flatMap(userId -> crudUserService.update(userId, command));
    }

    // ============================
    // GET /users/{id}
    // ============================

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user by their unique identifier"
    )
    @GetMapping("/{id}")
    public Mono<UserDto> getUserById(@PathVariable String id) {
        return crudUserService.getById(UUID.fromString(id));
    }

    // ============================
    // GET /users
    // ============================

    @Operation(
            summary = "List users with optional filters",
            description = "Returns a paginated list of users filtered by gender, role, and active status"
    )
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

    // ============================
    // DELETE /users/{id}
    // ============================

    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a user by their unique identifier"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteUser(@PathVariable String id) {
        return crudUserService.deleteById(UUID.fromString(id));
    }

    // ============================
    // DELETE /users/me
    // ============================

    @Operation(
            summary = "Delete authenticated user's account",
            description = "Deletes the account of the currently authenticated user"
    )
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (AuthUser) ctx.getAuthentication().getPrincipal())
                .map(AuthUser::getId)
                .flatMap(crudUserService::deleteById);
    }
}
