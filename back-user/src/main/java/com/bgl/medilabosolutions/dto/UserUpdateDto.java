package com.bgl.medilabosolutions.dto;

import com.bgl.medilabosolutions.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Email String email,
        @NotNull Role role) {

    public UserUpdateDto {
        email = email == null ? null : email.trim().toLowerCase();
    }
}
