package com.escaes.ms_users_jobsi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "john.doe@example.com")
    String email;

    @NotBlank
    @Schema(example = "123")
    String password;

}
