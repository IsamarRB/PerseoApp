package com.perseo.ControllerTest;

import com.perseo.controllers.UserCoursesController;
import com.perseo.models.UserCourses;
import com.perseo.services.UserCoursesService;
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

public class UserCoursesControllerTest {
    @Mock
    private UserCoursesService userCoursesService;

    @InjectMocks
    private UserCoursesController userCoursesController;

    private UserCourses userCourses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userCourses = new UserCourses();
        userCourses.setUserCoursesId(1);
        userCourses.setPurchaseDate(20210901);
    }

    @Test
    void testCreateUserCourses() {
        when(userCoursesService.createUserCourses(userCourses)).thenReturn(userCourses);

        UserCourses createdUserCourses = userCoursesController.createUserCourses(userCourses);

        assertNotNull(createdUserCourses);
        assertEquals(userCourses.getUserCoursesId(), createdUserCourses.getUserCoursesId());
        verify(userCoursesService, times(1)).createUserCourses(userCourses);
    }

    @Test
    void testGetUserCoursesById() {
        when(userCoursesService.getUserCoursesById(1)).thenReturn(Optional.of(userCourses));

        ResponseEntity<UserCourses> response = userCoursesController.getUserCoursesById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userCourses.getUserCoursesId(), response.getBody().getUserCoursesId());
        verify(userCoursesService, times(1)).getUserCoursesById(1);
    }

    @Test
    void testGetAllUserCourses() {
        List<UserCourses> userCoursesList = new ArrayList<>();
        userCoursesList.add(userCourses);

        when(userCoursesService.getAllUserCourses()).thenReturn(userCoursesList);

        List<UserCourses> allUserCourses = userCoursesController.getAllUserCourses();

        assertNotNull(allUserCourses);
        assertEquals(1, allUserCourses.size());
        verify(userCoursesService, times(1)).getAllUserCourses();
    }

    @Test
    void testUpdateUserCourses() {
        when(userCoursesService.updateUserCourses(1, userCourses)).thenReturn(userCourses);

        ResponseEntity<UserCourses> response = userCoursesController.updateUserCourses(1, userCourses);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userCourses.getUserCoursesId(), response.getBody().getUserCoursesId());
        verify(userCoursesService, times(1)).updateUserCourses(1, userCourses);
    }

    @Test
    void testDeleteUserCourses() {
        doNothing().when(userCoursesService).deleteUserCourses(1);

        ResponseEntity<Void> response = userCoursesController.deleteUserCourses(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userCoursesService, times(1)).deleteUserCourses(1);
    }
}
