package com.escaes.ms_users_jobsi.adapter.out.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.escaes.ms_users_jobsi.application.service.CrudUserService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTutil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        if (!jwtUtil.validateToken(token)) {
            return Mono.error(new AuthenticationException("Invalid token") {});
        }

        UUID id = jwtUtil.extractUserId(token);
        String email = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        AuthUser principal = new AuthUser(id,email, role);

        return Mono.just(
                new UsernamePasswordAuthenticationToken(principal, token, authorities)
        );
    }
    
    public ServerAuthenticationConverter authenticationConverter() {
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    // create authentication with token as credentials; principal will be resolved by authenticate()
                    return Mono.just(new UsernamePasswordAuthenticationToken(null, token));
                }
                return Mono.empty();
            }
        };
    }

}
