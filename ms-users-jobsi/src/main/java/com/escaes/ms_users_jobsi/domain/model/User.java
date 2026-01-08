package com.escaes.ms_users_jobsi.domain.model;


import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User {

    private UUID id;
    private String documentNumber;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthDate;
    private boolean isActive;
    private Role role;
    private Gender gender;

}
