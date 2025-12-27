package com.escaes.ms_users_jobsi.application.dto;

import java.util.Date;
import java.util.UUID;

import com.escaes.ms_users_jobsi.domain.model.Gender;
import com.escaes.ms_users_jobsi.domain.model.Role;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    UUID id;
    String documentNumber;
    String name;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Date birthDate;
    boolean isActive;
    Role role;
    Gender gender;

}
