package com.bgl.medilabosolutions.services;

import com.bgl.medilabosolutions.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import com.bgl.medilabosolutions.dto.UserRequestDto;
import com.bgl.medilabosolutions.dto.UserResponseDto;
import com.bgl.medilabosolutions.dto.UserUpdateDto;
import com.bgl.medilabosolutions.entities.UserEntity;
import com.bgl.medilabosolutions.mappers.UserMapper;
import com.bgl.medilabosolutions.repositories.UserRepository;
import com.bgl.medilabosolutions.exception.EmailAlreadyUsedException;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Create
    public UserResponseDto createUser(UserRequestDto dto) {
        // Check if email already exists
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException(dto.email());
        }

        UserEntity userEntity = userMapper.toEntity(dto);
        userEntity.setPassword(passwordEncoder.encode(dto.password()));
        return userMapper.toDto(userRepository.save(userEntity));

    }

    // GetAll
    public List<UserResponseDto> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(userMapper::toDto).toList();
    }

    // GetById
    public UserResponseDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.toDto(userEntity);
    }

    // Delete
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Update
    public UserResponseDto update(Long id, UserUpdateDto dto) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userMapper.updateEntity(userEntity, dto);
        return userMapper.toDto(userEntity);
    }
}
