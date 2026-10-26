package com.bgl.medilabosolutions.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {

    public LoginRequest {
        email = email == null ? null : email.trim().toLowerCase();
    }

    @Override
    public String toString() {   // ne jamais afficher le mot de passe
        return "LoginRequest[email=" + email + "]";
    }
}