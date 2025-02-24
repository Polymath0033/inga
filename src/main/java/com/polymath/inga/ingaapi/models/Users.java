package com.polymath.inga.ingaapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotBlank(message = "Email field can not be blank")
    @Email(message = "Please enter a valid email address")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 5,message = "The password must have at least five(5) characters")
    private String password;
    private String token;
    private LocalDateTime tokenExpiration;
}
