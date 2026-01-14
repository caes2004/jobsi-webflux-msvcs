package com.escaes.ms_users_jobsi.application.dto;

import java.time.LocalDate;

import com.escaes.ms_users_jobsi.domain.model.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Schema(description = "Command used to register a new user in the system")
public class RegisterUserCommand {

    @NotBlank(message = "Document number is required")
    @Schema(
            example = "11078346",
            description = "Unique document number used to identify the user"
    )
    String documentNumber;

    @NotBlank(message = "Name is required")
    @Schema(
            example = "Esteban",
            description = "User's first given name"
    )
    String name;

    @NotBlank(message = "First name is required")
    @Schema(
            example = "Cano",
            description = "User's first family name"
    )
    String firstName;

    @Schema(
            example = "Estrada",
            description = "User's second family name (optional)"
    )
    String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is not valid")
    @Schema(
            example = "ecano@mail.com.co",
            description = "User's email address used for authentication"
    )
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Schema(
            example = "C1234567",
            description = "User password (minimum 8 characters)"
    )
    String password;

    @NotBlank(message = "Phone number is required")
    @Schema(
            example = "3145007071",
            description = "User's contact phone number"
    )
    String phoneNumber;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @Schema(
            example = "2004-02-07",
            description = "Birth date in ISO-8601 format (yyyy-MM-dd)"
    )
    LocalDate birthDate;

    @Schema(
            description = "User gender",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE", "OTHER"}
    )
    Gender gender;
}
