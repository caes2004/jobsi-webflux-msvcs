package com.escaes.ms_users_jobsi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jobsi-Users API")
                        .version("2.0")
                        .description(
                                "User Management microservice for the Jobsi ecosystem. "
                                        + "This service handles user authentication, authorization, and lifecycle management, "
                                        + "including secure credential storage, role validation, and identity verification. "
                                        + "It serves as the central authority for user-related operations across the platform."
                        )
                );
    }
}
