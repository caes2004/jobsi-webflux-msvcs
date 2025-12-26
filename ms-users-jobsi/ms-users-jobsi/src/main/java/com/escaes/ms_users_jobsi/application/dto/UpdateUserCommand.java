package com.escaes.ms_users_jobsi.application.dto;

import java.time.LocalDate;

import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;

import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class UpdateUserCommand {

    String name;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthDate;
    Boolean isActive;
    Role role;
    Gender gender;

}
