package com.escaes.ms_users_jobsi.application.service;

import java.util.UUID;


import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import com.escaes.ms_users_jobsi.domain.port.in.DeleteUserUseCase;
import com.escaes.ms_users_jobsi.domain.port.in.GetUserUseCase;
import com.escaes.ms_users_jobsi.domain.port.in.ListUsersUseCase;
import com.escaes.ms_users_jobsi.domain.port.in.UpdateUserUseCase;
import com.escaes.ms_users_jobsi.domain.port.out.UserRepositoryPort;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@AllArgsConstructor
public class CrudUserService implements GetUserUseCase,ListUsersUseCase,UpdateUserUseCase,DeleteUserUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    public Mono<Void> deleteById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Mono<Void> deleteByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByEmail'");
    }

    @Override
    public Mono<Void> deleteByDocumentNumber(String documentNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByDocumentNumber'");
    }

    @Override
    public Mono<UserDto> update(UUID id, UpdateUserCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Flux<UserDto> listAll() {
        return userRepository.findAll()
                .map(user-> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .lastName(user.getLastName())
                        .documentNumber(user.getDocumentNumber())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .isActive(user.isActive())
                        .role(user.getRole() != null ? user.getRole() : null)
                        .gender(user.getGender() != null ? user.getGender() : null)
                        .build());
    }

    @Override
    public Flux<UserDto> listByRole(String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listByRole'");
    }

    @Override
    public Flux<UserDto> listByIsActive(boolean isActive) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listByIsActive'");
    }

    @Override
    public Flux<UserDto> listByGender(String gender) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listByGender'");
    }

    @Override
    public Mono<UserDto> getById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Mono<UserDto> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user-> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .lastName(user.getLastName())
                        .documentNumber(user.getDocumentNumber())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .isActive(user.isActive())
                        .role(user.getRole() != null ? user.getRole() : null)
                        .gender(user.getGender() != null ? user.getGender() : null)
                        .build());
    }

    @Override
    public Mono<UserDto> getByDocumentNumber(String documentNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByDocumentNumber'");
    }

    @Override
    public Mono<Integer> countUsers() {
        return userRepository.countUsers();
    }

}
