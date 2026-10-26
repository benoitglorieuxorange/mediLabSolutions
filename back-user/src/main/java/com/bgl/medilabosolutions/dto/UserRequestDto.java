package com.bgl.medilabosolutions.dto;

import com.bgl.medilabosolutions.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//Creation
public record UserRequestDto(

        String firstName,
        String lastName,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 72, message = "Le mot de passe doit contenir entre 8 et 72 caractères") @Pattern(regexp = "^(?=.*\\p{N})(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*[^\\p{L}\\p{N}\\s])(?=\\S+$).+$", message = "Le mot de passe doit contenir une majuscule, une minuscule, un chiffre et un caractère spécial, sans espace") String password,
        @NotNull Role role) {

    // Normalisation de l'email à un seul endroit
    public UserRequestDto {
        email = email == null ? null : email.trim().toLowerCase();
    }

    // Le toString généré d'un record afficherait le mot de passe dans les logs
    @Override
    public String toString() {
        return "UserRequestDto[email=" + email + ", role=" + role + "]";
    }

}
