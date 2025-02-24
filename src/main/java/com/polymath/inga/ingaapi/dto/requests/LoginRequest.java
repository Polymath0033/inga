package com.polymath.inga.ingaapi.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotNull @NotBlank(message = "Email can not be blank") @Email(message = "Enter a valid") String email, @NotNull @NotBlank(message = "password cannot be blank") @Size(min = 5,message = "password must be at least of 5 characters") String password) {
}
