package com.escaes.ms_users_jobsi.config;

import java.util.logging.Logger;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.application.service.CrudUserService;
import com.escaes.ms_users_jobsi.application.service.RegisterUserService;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;


@Configuration
@Profile("dev")
public class DataInitializer {

    private static final Logger logger =
            Logger.getLogger(DataInitializer.class.getName());

    @Bean
    ApplicationRunner init(
            RegisterUserService registerUserService,
            CrudUserService crudUserService
    ) {
        return args -> crudUserService.countUsers()
                .filter(count -> count == 0)
                .flatMapMany(count -> {
                    logger.info("Initializing sample users...");

                    return Flux.concat(
                            registerUserService.registerUser(
                                    RegisterUserCommand.builder()
                                            .name("John")
                                            .firstName("Doe")
                                            .lastName("peggy")
                                            .email("john.doe@example.com")
                                            .password("123")
                                            .phoneNumber("+1234567890")
                                            .build()
                            ),
                            registerUserService.registerUser(
                                    RegisterUserCommand.builder()
                                            .name("Jane")
                                            .firstName("Street")
                                            .lastName("Smith")
                                            .email("jane.smith@example.com")
                                            .password("123")
                                            .phoneNumber("+0987654321")
                                            .build()
                            )
                    );
                })
                .doOnComplete(() -> logger.info("Sample users initialized."))
                .subscribe();
    }
}

