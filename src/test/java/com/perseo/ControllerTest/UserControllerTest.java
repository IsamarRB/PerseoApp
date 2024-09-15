package com.perseo.ControllerTest;

import com.perseo.controllers.UserController;
import com.perseo.models.User;
import com.perseo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(user)).thenReturn(user);

        User createdUser = userController.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getUserId(), createdUser.getUserId());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUserId(), response.getBody().getUserId());
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userService.getAllUsers()).thenReturn(userList);

        List<User> allUsers = userController.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(1, user)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(1, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUserId(), response.getBody().getUserId());
        verify(userService, times(1)).updateUser(1, user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1);

        ResponseEntity<Void> response = userController.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    void testGetUserByEmail() {
        when(userService.getUserByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserByEmail("testuser@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getEmail(), response.getBody().getEmail());
        verify(userService, times(1)).getUserByEmail("testuser@example.com");
    }
}
