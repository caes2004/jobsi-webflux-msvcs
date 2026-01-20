package com.escaes.ms_users_jobsi.application.service;

import java.util.UUID;


import com.escaes.ms_users_jobsi.application.dto.UpdateUserCommand;
import com.escaes.ms_users_jobsi.application.dto.UserDto;
import com.escaes.ms_users_jobsi.application.mapper.UserMapper;
import com.escaes.ms_users_jobsi.domain.exception.UserNotFoundException;
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

    private static final int MAX_PAGE_SIZE = 50;

    @Override
    public Mono<Void> deleteById(UUID id) {
       return userRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }

    @Override
    public Mono<Void> deleteByDocumentNumber(String documentNumber) {
        return  userRepository.deleteByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<UserDto> update(UUID id, UpdateUserCommand command) {

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with Id: "+id)))
                .map(user -> UserMapper.updateFromCommand(user, command))
                .flatMap(userRepository::update)
                .map(UserMapper::toDto);
    }

    @Override
    public Flux<UserDto> listAll(int page, int size) {
        int safeSize=Math.min(size,MAX_PAGE_SIZE);
        return userRepository.findAll(page,safeSize)
                .map(UserMapper::toDto);
    }

    @Override
    public Flux<UserDto> listByRole(String role, int size, int page) {
        int safeSize=Math.min(size,MAX_PAGE_SIZE);
        return userRepository.findAllByRole(role, page, safeSize).map(UserMapper::toDto);
    }

    @Override
    public Flux<UserDto> listByIsActive(boolean isActive, int page, int size) {
        int safeSize=Math.min(size,MAX_PAGE_SIZE);
        return userRepository.findAllByIsActive(isActive, page, safeSize).map(UserMapper::toDto);
    }

    @Override
    public Flux<UserDto> listByGender(String gender, int size, int page) {
        int safeSize= Math.min(size,50);
        return userRepository.findAllByGender(gender,page, safeSize).map(UserMapper::toDto);
    }

    @Override
    public Flux<UserDto> findUsersCriteria(String gender, String role, Boolean active, int page, int size) {
        int safeSize=Math.min(size,MAX_PAGE_SIZE);
        return userRepository.findUsersCriteria(gender, role, active, page, safeSize).map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> getById(UUID id) {
      return userRepository.findById(id).map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> getByDocumentNumber(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber).map(UserMapper::toDto);
    }

    @Override
    public Mono<Integer> countUsers() {
        return userRepository.countUsers();
    }

}
