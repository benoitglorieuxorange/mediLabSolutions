package com.bgl.medilabosolutions.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.bgl.medilabosolutions.dto.UserRequestDto;
import com.bgl.medilabosolutions.dto.UserResponseDto;
import com.bgl.medilabosolutions.dto.UserUpdateDto;
import com.bgl.medilabosolutions.entities.UserEntity;
import com.bgl.medilabosolutions.enums.Role;
import com.bgl.medilabosolutions.mappers.UserMapper;
import com.bgl.medilabosolutions.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, new UserMapper(), passwordEncoder);
    }

    @Test
    void createUserSavesEncodedPasswordAndReturnsUser() {
        UserRequestDto request = new UserRequestDto("Jane", "Doe", "jane@example.com", "Password1!", Role.USER);
        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encoded-password");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity userEntity = invocation.getArgument(0);
            userEntity.setId(1L);
            return userEntity;
        });

        UserResponseDto result = userService.createUser(request);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUserEntity = userCaptor.getValue();
        assertEquals("encoded-password", savedUserEntity.getPassword());
        assertEquals(new UserResponseDto(1L, "Jane", "Doe", "jane@example.com", Role.USER), result);
    }

    @Test
    void createUserRejectsDuplicateEmail() {
        UserRequestDto request = new UserRequestDto("Jane", "Doe", "jane@example.com", "Password1!", Role.USER);
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(request));

        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void getAllUserReturnsMappedUsers() {
        UserEntity jane = new UserEntity("Jane", "Doe", "jane@example.com", Role.USER);
        jane.setId(1L);
        UserEntity john = new UserEntity("John", "Doe", "john@example.com", Role.ADMIN);
        john.setId(2L);
        when(userRepository.findAll()).thenReturn(List.of(jane, john));

        List<UserResponseDto> result = userService.getAllUser();

        assertEquals(List.of(
                new UserResponseDto(1L, "Jane", "Doe", "jane@example.com", Role.USER),
                new UserResponseDto(2L, "John", "Doe", "john@example.com", Role.ADMIN)), result);
        verify(userRepository).findAll();
    }

    @Test
    void getUserByIdReturnsMappedUser() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity("Jane", "Doe", "jane@example.com", Role.USER);
        userEntity.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        UserResponseDto result = userService.getUserById(id);

        assertEquals(new UserResponseDto(id, "Jane", "Doe", "jane@example.com", Role.USER), result);
    }

    @Test
    void getUserByIdThrowsWhenUserDoesNotExist() {
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserById(id));

        assertEquals("User not found with id: 99", exception.getMessage());
    }

    @Test
    void deleteUserDeletesExistingUser() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        userService.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUserThrowsWhenUserDoesNotExist() {
        Long id = 99L;
        when(userRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(id));

        assertEquals("User not found with id: 99", exception.getMessage());
        verify(userRepository, never()).deleteById(id);
    }

    @Test
    void updateUserChangesAndReturnsUser() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity("Jane", "Doe", "jane@example.com", Role.USER);
        userEntity.setId(id);
        UserUpdateDto update = new UserUpdateDto("Janet", "Smith", "janet@example.com", Role.ADMIN);
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        UserResponseDto result = userService.update(id, update);

        assertEquals(new UserResponseDto(id, "Janet", "Smith", "janet@example.com", Role.ADMIN), result);
    }

    @Test
    void updateUserThrowsWhenUserDoesNotExist() {
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        UserUpdateDto update = new UserUpdateDto("Janet", "Smith", "janet@example.com", Role.ADMIN);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.update(id, update));
        assertEquals("User not found with id: 99", exception.getMessage());
        assertEquals("User not found with id: 99", exception.getMessage());
    }
}
