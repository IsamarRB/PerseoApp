package com.perseo.ServicesTest;

import com.perseo.models.UserCourses;
import com.perseo.repositories.IUserCoursesRepository;
import com.perseo.services.UserCoursesService;
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

public class UserCoursesServiceTest {
    @Mock
    private IUserCoursesRepository iUserCoursesRepository;

    @InjectMocks
    private UserCoursesService userCoursesService;

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
        when(iUserCoursesRepository.save(userCourses)).thenReturn(userCourses);

        UserCourses createdUserCourses = userCoursesService.createUserCourses(userCourses);

        assertNotNull(createdUserCourses);
        assertEquals(userCourses.getUserCoursesId(), createdUserCourses.getUserCoursesId());
        verify(iUserCoursesRepository, times(1)).save(userCourses);
    }

    @Test
    void testGetUserCoursesById() {
        when(iUserCoursesRepository.findById(1)).thenReturn(Optional.of(userCourses));

        Optional<UserCourses> foundUserCourses = userCoursesService.getUserCoursesById(1);

        assertTrue(foundUserCourses.isPresent());
        assertEquals(userCourses.getUserCoursesId(), foundUserCourses.get().getUserCoursesId());
        verify(iUserCoursesRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllUserCourses() {
        List<UserCourses> userCoursesList = new ArrayList<>();
        userCoursesList.add(userCourses);

        when(iUserCoursesRepository.findAll()).thenReturn(userCoursesList);

        List<UserCourses> allUserCourses = userCoursesService.getAllUserCourses();

        assertEquals(1, allUserCourses.size());
        verify(iUserCoursesRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUserCourses() {
        when(iUserCoursesRepository.findById(1)).thenReturn(Optional.of(userCourses));
        when(iUserCoursesRepository.save(userCourses)).thenReturn(userCourses);

        UserCourses updatedUserCourses = userCoursesService.updateUserCourses(1, userCourses);

        assertEquals(userCourses.getPurchaseDate(), updatedUserCourses.getPurchaseDate());
        verify(iUserCoursesRepository, times(1)).save(userCourses);
    }

    @Test
    void testDeleteUserCourses() {
        userCoursesService.deleteUserCourses(1);
        verify(iUserCoursesRepository, times(1)).deleteById(1);
    }
}
