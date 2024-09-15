package com.perseo.ServicesTest;

import com.perseo.models.User;
import com.perseo.repositories.IUserRepository;
import com.perseo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository iUserRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Instanciamos un usuario de ejemplo para usar en las pruebas
        user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        // Puedes añadir otros campos según tu modelo de User
    }

    @Test
    void testCreateUser() {
        // Simulamos que el repositorio guarda el usuario correctamente
        when(iUserRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getUserId(), createdUser.getUserId());
        assertEquals("testuser", createdUser.getUsername());
        verify(iUserRepository, times(1)).save(user); // Verificamos que el repositorio se llama una vez
    }

    @Test
    void testGetUserById() {
        // Simulamos que el repositorio encuentra un usuario por su ID
        when(iUserRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1);

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        verify(iUserRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserByIdNotFound() {
        // Simulamos que no se encuentra un usuario con el ID especificado
        when(iUserRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(1);

        assertFalse(foundUser.isPresent());
        verify(iUserRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllUsers() {
        // Creamos una lista de usuarios para simular los datos del repositorio
        List<User> users = new ArrayList<>();
        users.add(user);

        when(iUserRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(1, allUsers.size());
        verify(iUserRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {
        // Simulamos que el repositorio encuentra el usuario y lo actualiza
        when(iUserRepository.findById(1)).thenReturn(Optional.of(user));
        when(iUserRepository.save(user)).thenReturn(user);

        // Datos actualizados
        User updatedDetails = new User();
        updatedDetails.setUsername("updatedUser");
        updatedDetails.setEmail("updated@example.com");
        updatedDetails.setPassword("newPassword");

        User updatedUser = userService.updateUser(1, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updated@example.com", updatedUser.getEmail());
        verify(iUserRepository, times(1)).findById(1);
        verify(iUserRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserNotFound() {
        // Simulamos que no se encuentra un usuario con el ID especificado
        when(iUserRepository.findById(1)).thenReturn(Optional.empty());

        // Verificamos que se lanza una excepción cuando el usuario no existe
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1, user);
        });

        assertEquals("User not found", exception.getMessage());
        verify(iUserRepository, times(1)).findById(1);
        verify(iUserRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Ejecutamos el método deleteUser y verificamos que se llama al repositorio para eliminar por ID
        userService.deleteUser(1);
        verify(iUserRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetUserByEmail() {
        // Simulamos que el repositorio encuentra un usuario por su correo electrónico
        when(iUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail("testuser@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
        verify(iUserRepository, times(1)).findByEmail("testuser@example.com");
    }

    @Test
    void testGetUserByEmailNotFound() {
        // Simulamos que no se encuentra un usuario con el correo electrónico especificado
        when(iUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserByEmail("testuser@example.com");

        assertFalse(foundUser.isPresent());
        verify(iUserRepository, times(1)).findByEmail("testuser@example.com");
    }
}
