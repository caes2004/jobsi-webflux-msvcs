package com.escaes.ms_users_jobsi.adapter.out.persistence;

import java.util.UUID;

import com.escaes.ms_users_jobsi.adapter.out.persistence.entity.UserEntity;
import com.escaes.ms_users_jobsi.domain.exception.UserNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
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
        Mono<UserEntity>userEntityMono=r2dbcUserRepository.findById(id.toString())
                .switchIfEmpty(
                        Mono.error(new UserNotFoundException("User not found with id: "+id))
                );
        return userEntityMono.map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return r2dbcUserRepository.existsById(id.toString());
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return findById(id)
                .map(UserMapper::toEntity)
                .flatMap(r2dbcUserRepository::delete)
                .then();
    }

    @Override
    public Flux<User> findAll(int size, int page) {
        Query query= Query.empty()
                .with(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "email")));

        return template.select(UserEntity.class)
                .matching(query)
                .all().map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return r2dbcUserRepository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
        return template.select(UserEntity.class)
                .matching(Query.query(Criteria.where("document_number").is(documentNumber)))
                .one()
                .hasElement();
    }

    @Override
    public Mono<User> findByEmail(String email) {
        Mono<UserEntity> userEntityMono = r2dbcUserRepository.findByEmail(email).switchIfEmpty(
                Mono.error(new UserNotFoundException("User not found with email: " + email))
        );
        return userEntityMono.map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<User> findByDocumentNumber(String documentNumber) {
        Mono<UserEntity> userEntityMono = r2dbcUserRepository.findByDocumentNumber(documentNumber).switchIfEmpty(
                Mono.error(new UserNotFoundException("User not found with documentNumber: " + documentNumber))
        );
        return userEntityMono.map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<Void> deleteByDocumentNumber(String documentNumber) {
        Mono<UserEntity>user=template.select(UserEntity.class)
                .matching(Query.query(Criteria.where("document_number").is(documentNumber)))
                .one()
                .switchIfEmpty(
                        Mono.error(new UserNotFoundException("User not found with document_number: " + documentNumber))
                );
        return template.delete(user).then();
    }

    @Override
    public Mono<Void> deleteByEmail(String email) {
        Mono<UserEntity>user=template
                .select(UserEntity.class)
                .matching(Query.query(Criteria.where("email").is(email)))
                .one()
                .switchIfEmpty(
                        Mono.error(new UserNotFoundException("User not found with email: " + email))
                );

        return template.delete(user).then();
    }

    @Override
    public Flux<User> findAllByRole(String role, int size, int page) {
        Query query = Query.query(Criteria.where("role").is(role))
                .with(PageRequest.of(size,page,Sort.by(Sort.Direction.ASC,"email")));

       Flux<UserEntity>users= template
               .select(UserEntity.class)
               .matching(query)
               .all()
               .switchIfEmpty(
                       Flux.error(new UserNotFoundException("Users not found with role: " + role))
               );
       return users.map(UserMapper::EntityToDomain);
    }

    @Override
    public Flux<User> findAllByIsActive(boolean isActive, int page, int size) {

        Query query= Query.query(Criteria.where("isActive").is(isActive))
                .with(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"email")));

        return template.select(UserEntity.class)
                .matching(query)
                .all()
                .map(UserMapper::EntityToDomain);
    }

    @Override
    public Flux<User> findAllByGender(String gender, int page, int size) {
        Query query=Query.query(Criteria.where("gender").is(gender))
                .with(PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,"email")));

        return template.select(UserEntity.class)
                .matching(query)
                .all()
                .switchIfEmpty(
                         Flux.error(new UserNotFoundException("Users not found with gender: "+gender))
                )
                .map(UserMapper::EntityToDomain);
    }

    @Override
    public Mono<Integer> countUsers() {
        return r2dbcUserRepository.count()
                .map(Long::intValue);
    }

}
