package com.escaes.ms_users_jobsi.adapter.in.config;

import com.escaes.ms_users_jobsi.adapter.out.security.JWTutil;
import com.escaes.ms_users_jobsi.application.service.AuthenticateUserService;
import com.escaes.ms_users_jobsi.application.service.CrudUserService;
import com.escaes.ms_users_jobsi.application.service.RegisterUserService;
import com.escaes.ms_users_jobsi.domain.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Application layer configuration.
 *
 * This configuration class is responsible for wiring application use cases
 * with their required dependencies.
 *
 * Why is this done here and not in the service itself?
 * - The application layer must remain framework-agnostic.
 * - Annotating services with @Service would introduce a dependency on Spring.
 * - In Clean Architecture, object creation is delegated to the outer layers.
 *
 * This class belongs to the adapter/config layer, which is allowed to depend
 * on Spring and acts as the composition root of the application.
 *
 * As a result:
 * - The application layer stays clean and testable.
 * - Spring is only used at the boundaries.
 * - The system is easier to maintain and evolve.
 */
@Configuration
public class UserApplicationConfig {

    @Bean
    public CrudUserService crudUserService(
            UserRepositoryPort userRepositoryPort
    ) {
        return new CrudUserService(userRepositoryPort);
    }
    @Bean
    public AuthenticateUserService authenticateUserService(
            UserRepositoryPort userRepositoryPort,
            JWTutil jwtutil,
            BCryptPasswordEncoder passwordEncoder){
        return new AuthenticateUserService(userRepositoryPort,jwtutil,passwordEncoder);
    }
    @Bean
    public RegisterUserService registerUserService(
            UserRepositoryPort userRepositoryPort
    ){
        return new RegisterUserService(userRepositoryPort);
    }

}
