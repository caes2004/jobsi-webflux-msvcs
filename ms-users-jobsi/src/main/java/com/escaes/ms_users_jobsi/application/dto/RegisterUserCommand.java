package com.escaes.ms_users_jobsi.application.dto;


import java.time.LocalDate;

import com.escaes.ms_users_jobsi.domain.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class RegisterUserCommand {

    @NotBlank
    String documentNumber;

    @NotBlank
    String name;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Email
    @NotBlank
    String email;

    @NotBlank
    @Size(min = 8)
    String password;

    String phoneNumber;

    @Past
    LocalDate birthDate;

    Gender gender;
}
