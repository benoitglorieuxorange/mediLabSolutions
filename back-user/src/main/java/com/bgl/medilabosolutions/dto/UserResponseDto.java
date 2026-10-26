package com.bgl.medilabosolutions.dto;

import com.bgl.medilabosolutions.enums.Role;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role) {
}
