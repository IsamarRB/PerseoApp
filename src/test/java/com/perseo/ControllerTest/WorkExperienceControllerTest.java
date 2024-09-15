package com.perseo.ControllerTest;

import com.perseo.controllers.WorkExperienceController;
import com.perseo.models.WorkExperience;
import com.perseo.services.WorkExperienceService;
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

public class WorkExperienceControllerTest {
    @Mock
    private WorkExperienceService workExperienceService;

    @InjectMocks
    private WorkExperienceController workExperienceController;

    private WorkExperience workExperience;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        workExperience = WorkExperience.builder()
                .workExperienceId(1)
                .companyName("Tech Company")
                .position("Developer")
                .yearsOfExperience(5)
                .build();
    }

    @Test
    void testCreateWorkExperience() {
        when(workExperienceService.createWorkExperience(workExperience)).thenReturn(workExperience);

        WorkExperience createdWorkExperience = workExperienceController.createWorkExperience(workExperience);

        assertNotNull(createdWorkExperience);
        assertEquals(workExperience.getWorkExperienceId(), createdWorkExperience.getWorkExperienceId());
        verify(workExperienceService, times(1)).createWorkExperience(workExperience);
    }

    @Test
    void testGetWorkExperienceById() {
        when(workExperienceService.getWorkExperienceById(1)).thenReturn(Optional.of(workExperience));

        ResponseEntity<WorkExperience> response = workExperienceController.getWorkExperienceById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(workExperience.getWorkExperienceId(), response.getBody().getWorkExperienceId());
        verify(workExperienceService, times(1)).getWorkExperienceById(1);
    }

    @Test
    void testGetAllWorkExperiences() {
        List<WorkExperience> workExperienceList = new ArrayList<>();
        workExperienceList.add(workExperience);

        when(workExperienceService.getAllWorkExperiences()).thenReturn(workExperienceList);

        List<WorkExperience> allWorkExperiences = workExperienceController.getAllWorkExperiences();

        assertNotNull(allWorkExperiences);
        assertEquals(1, allWorkExperiences.size());
        verify(workExperienceService, times(1)).getAllWorkExperiences();
    }

    @Test
    void testGetWorkExperiencesByUserId() {
        List<WorkExperience> workExperienceList = new ArrayList<>();
        workExperienceList.add(workExperience);

        when(workExperienceService.getWorkExperiencesByUserId(1)).thenReturn(workExperienceList);

        List<WorkExperience> foundWorkExperiences = workExperienceController.getWorkExperiencesByUserId(1);

        assertNotNull(foundWorkExperiences);
        assertEquals(1, foundWorkExperiences.size());
        verify(workExperienceService, times(1)).getWorkExperiencesByUserId(1);
    }

    @Test
    void testUpdateWorkExperience() {
        when(workExperienceService.updateWorkExperience(1, workExperience)).thenReturn(workExperience);

        ResponseEntity<WorkExperience> response = workExperienceController.updateWorkExperience(1, workExperience);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(workExperience.getCompanyName(), response.getBody().getCompanyName());
        verify(workExperienceService, times(1)).updateWorkExperience(1, workExperience);
    }

    @Test
    void testDeleteWorkExperience() {
        doNothing().when(workExperienceService).deleteWorkExperience(1);

        ResponseEntity<Void> response = workExperienceController.deleteWorkExperience(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(workExperienceService, times(1)).deleteWorkExperience(1);
    }
}
