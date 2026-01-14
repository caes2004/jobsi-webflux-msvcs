package com.escaes.ms_users_jobsi.adapter.out.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Table("users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    
    @Id
    private String id;

    @Column("document_number")
    private String documentNumber;

    @Column("first_name")
    private String firstName; 

    @Column("last_name")
    private String lastName;

    @Column("name")
    private String name;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("phone_number")
    private String phoneNumber;

    @Column("birth_date") 
    private LocalDate birthDate;

    @Column("is_active")
    private boolean isActive;

    @Column("role")
    private String role;

    @Column("gender")
    private String gender;

}
