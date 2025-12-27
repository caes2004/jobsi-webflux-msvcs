package com.escaes.ms_users_jobsi.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class LoginCommand {
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;

}
