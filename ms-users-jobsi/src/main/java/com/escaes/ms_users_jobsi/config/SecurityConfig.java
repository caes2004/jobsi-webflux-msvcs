package com.escaes.ms_users_jobsi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import com.escaes.ms_users_jobsi.adapter.out.security.JWTAuthenticationManager;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
            JWTAuthenticationManager jwtAuthManager) {
        AuthenticationWebFilter authFilter = new AuthenticationWebFilter(jwtAuthManager);

        authFilter.setServerAuthenticationConverter(jwtAuthManager.authenticationConverter());
        ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();
        authFilter.setSecurityContextRepository(securityContextRepository);

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .headers(headers -> headers.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**", "/actuator/**", "/health","/v3/api-docs/**","/swagger-ui/**").permitAll()
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .pathMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                        .anyExchange().authenticated())
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
