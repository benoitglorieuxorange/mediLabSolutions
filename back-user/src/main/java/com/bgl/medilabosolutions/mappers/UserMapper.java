package com.bgl.medilabosolutions.mappers;

import com.bgl.medilabosolutions.entities.UserEntity;
import org.springframework.stereotype.Component;

import com.bgl.medilabosolutions.dto.UserRequestDto;
import com.bgl.medilabosolutions.dto.UserResponseDto;
import com.bgl.medilabosolutions.dto.UserUpdateDto;

@Component
public class UserMapper {

    public UserResponseDto toDto(UserEntity userEntity) {

        if (userEntity == null) {
            return null;
        }

        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getRole());
    }

    public UserEntity toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserEntity(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.role());
    }

    public void updateEntity(UserEntity userEntity, UserUpdateDto dto) {
        if (dto == null || userEntity == null) {
            return;
        }
        userEntity.setFirstName(dto.firstName());
        userEntity.setLastName(dto.lastName());
        userEntity.setEmail(dto.email());
        userEntity.setRole(dto.role());
    }

}
