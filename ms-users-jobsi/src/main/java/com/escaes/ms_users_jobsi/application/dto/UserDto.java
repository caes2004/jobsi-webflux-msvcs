package com.escaes.ms_users_jobsi.application.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserDto {

    UUID id;
    String documentNumber;
    String name;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    LocalDate birthDate;
    boolean isActive;
    Role role;
    Gender gender;

}
