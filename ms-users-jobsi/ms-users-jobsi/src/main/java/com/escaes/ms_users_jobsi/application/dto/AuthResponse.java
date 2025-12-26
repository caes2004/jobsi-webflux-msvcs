package com.escaes.ms_users_jobsi.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {

    String token;

}
