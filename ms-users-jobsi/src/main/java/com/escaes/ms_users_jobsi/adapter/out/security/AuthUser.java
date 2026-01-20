package com.escaes.ms_users_jobsi.adapter.out.security;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@RequiredArgsConstructor
public class AuthUser {
    UUID id;
    String email;
    String password;
}
