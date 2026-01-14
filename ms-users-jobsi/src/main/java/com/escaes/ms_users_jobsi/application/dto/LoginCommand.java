package com.escaes.ms_users_jobsi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Command used to log into the system")
public class LoginCommand {

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is not valid")
    @Schema(
            example = "john.doe@example.com",
            description = "User's email address used for authentication"
    )
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Schema(
            example = "A1234567",
            description = "User password (minimum 8 characters)"
    )
    String password;

}
