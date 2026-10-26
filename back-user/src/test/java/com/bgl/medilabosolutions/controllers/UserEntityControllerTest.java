package com.bgl.medilabosolutions.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.bgl.medilabosolutions.dto.UserRequestDto;
import com.bgl.medilabosolutions.dto.UserResponseDto;
import com.bgl.medilabosolutions.dto.UserUpdateDto;
import com.bgl.medilabosolutions.enums.Role;
import com.bgl.medilabosolutions.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserEntityControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUserReturnsCreatedUser() {
        UserRequestDto request = new UserRequestDto("Jane", "Doe", "jane@example.com", "Password1!", Role.USER);
        UserResponseDto response = new UserResponseDto(1L, "Jane", "Doe", "jane@example.com", Role.USER);
        when(userService.createUser(request)).thenReturn(response);

        ResponseEntity<UserResponseDto> result = userController.createUser(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(response, result.getBody());
        verify(userService).createUser(request);
    }

    @Test
    void updateUserReturnsUpdatedUser() {
        Long id = 1L;
        UserUpdateDto request = new UserUpdateDto("Jane", "Doe", "jane@example.com", Role.ADMIN);
        UserResponseDto response = new UserResponseDto(id, "Jane", "Doe", "jane@example.com", Role.ADMIN);
        when(userService.update(id, request)).thenReturn(response);

        ResponseEntity<UserResponseDto> result = userController.updateUser(id, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(response, result.getBody());
        verify(userService).update(id, request);
    }

    @Test
    void deleteUserReturnsNoContent() {
        Long id = 1L;

        ResponseEntity<Void> result = userController.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userService).deleteUser(id);
    }

    @Test
    void getAllUserReturnsAllUsers() {
        List<UserResponseDto> users = List.of(
                new UserResponseDto(1L, "Jane", "Doe", "jane@example.com", Role.USER),
                new UserResponseDto(2L, "John", "Doe", "john@example.com", Role.ADMIN));
        when(userService.getAllUser()).thenReturn(users);

        ResponseEntity<List<UserResponseDto>> result = userController.getAllUser();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(users, result.getBody());
        verify(userService).getAllUser();
    }

    @Test
    void getUserByIdReturnsMatchingUser() {
        Long id = 1L;
        UserResponseDto response = new UserResponseDto(id, "Jane", "Doe", "jane@example.com", Role.USER);
        when(userService.getUserById(id)).thenReturn(response);

        ResponseEntity<UserResponseDto> result = userController.getUserById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(response, result.getBody());
        verify(userService).getUserById(id);
    }
}