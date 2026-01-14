package com.escaes.ms_users_jobsi.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class LoginCommand {
    
    @Email
    @NotBlank
    String email;
    
    @NotBlank
    String password;

}
