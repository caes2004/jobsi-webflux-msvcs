package com.escaes.ms_users_jobsi.adapter.out.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

import java.util.Collections;
import java.util.List;

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

    private final CrudUserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
       String token = authentication.getCredentials().toString();
       String username= jwtUtil.extractUsername(token);

       return userService.getByEmail(username)
               .handle((userDetails, sink) -> {
                   if (jwtUtil.validateToken(token, userDetails.getEmail())) {
                       // Extract role from token and build authorities
                       String role = jwtUtil.extractRole(token);
                       List<GrantedAuthority> authorities = Collections.emptyList();
                       if (role != null) {
                           authorities = Collections.singletonList(
                                   new SimpleGrantedAuthority("ROLE_" + role)
                           );
                       }
                       sink.next(new UsernamePasswordAuthenticationToken(
                               userDetails.getEmail(), token, authorities));
                   } else {
                       sink.error(new AuthenticationException("Invalid Token") {
                       });
                   }
               });
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
