package com.escaes.ms_users_jobsi.config;

import java.util.logging.Logger;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.escaes.ms_users_jobsi.application.dto.RegisterUserCommand;
import com.escaes.ms_users_jobsi.application.service.CrudUserService;
import com.escaes.ms_users_jobsi.application.service.RegisterUserService;


@Configuration
public class DataInitializer {

    private final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    @Bean
    ApplicationRunner init(RegisterUserService service, CrudUserService crudUserService) {
        return args -> {
            if(crudUserService.countUsers().block() > 0) {
                logger.info("Users already exist. Skipping data initialization.");
                return;
            }
            // Initialize sample users
            logger.info("Initializing sample users...");
            service.registerUser(RegisterUserCommand.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .password("123")
                    .build());
            service.registerUser(RegisterUserCommand.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@example.com")
                    .password("123")
                    .build());

            logger.info("Sample users initialized.");
        };
    }
}
