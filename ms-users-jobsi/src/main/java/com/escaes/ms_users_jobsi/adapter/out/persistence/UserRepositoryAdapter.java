package com.escaes.ms_users_jobsi.adapter.out.persistence;

import java.util.UUID;

import com.escaes.ms_users_jobsi.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

import com.escaes.ms_users_jobsi.adapter.out.persistence.repository.R2dbcUserRepository;
import com.escaes.ms_users_jobsi.application.mapper.UserMapper;
import com.escaes.ms_users_jobsi.domain.model.User;
import com.escaes.ms_users_jobsi.domain.port.out.UserRepositoryPort;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final R2dbcUserRepository r2dbcUserRepository;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<User> create(User entity) {
        UserEntity userEntity = UserMapper.toEntity(entity);

        return template.insert(UserEntity.class).using(userEntity)
                .map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<User> update(User entity) {
        return r2dbcUserRepository.save(UserMapper.toEntity(entity))
                .map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<User> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Flux<User> findAll() {
        return r2dbcUserRepository.findAll()
                .map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return r2dbcUserRepository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByDocumentNumber'");
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return r2dbcUserRepository.findByEmail(email)
                .map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<User> findByDocumentNumber(String documentNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDocumentNumber'");
    }

    @Override
    public Mono<Void> deleteByDocumentNumber(String documentNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByDocumentNumber'");
    }

    @Override
    public Mono<Void> deleteByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByEmail'");
    }

    @Override
    public Flux<User> findAllByRole(String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByRole'");
    }

    @Override
    public Flux<User> findAllByIsActive(boolean isActive) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByIsActive'");
    }

    @Override
    public Flux<User> findAllByGender(String gender) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByGender'");
    }

    @Override
    public Mono<Integer> countUsers() {
        return r2dbcUserRepository.count()
                .map(Long::intValue);
    }

}
