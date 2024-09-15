package com.perseo.ServicesTest;

import com.perseo.models.WorkExperience;
import com.perseo.repositories.IWorkExperienceRepository;
import com.perseo.services.WorkExperienceService;
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

public class WorkExperienceServiceTest {
    @Mock
    private IWorkExperienceRepository iWorkExperienceRepository;

    @InjectMocks
    private WorkExperienceService workExperienceService;

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
        when(iWorkExperienceRepository.save(workExperience)).thenReturn(workExperience);

        WorkExperience createdWorkExperience = workExperienceService.createWorkExperience(workExperience);

        assertNotNull(createdWorkExperience);
        assertEquals(workExperience.getWorkExperienceId(), createdWorkExperience.getWorkExperienceId());
        verify(iWorkExperienceRepository, times(1)).save(workExperience);
    }

    @Test
    void testGetWorkExperienceById() {
        when(iWorkExperienceRepository.findById(1)).thenReturn(Optional.of(workExperience));

        Optional<WorkExperience> foundWorkExperience = workExperienceService.getWorkExperienceById(1);

        assertTrue(foundWorkExperience.isPresent());
        assertEquals(workExperience.getWorkExperienceId(), foundWorkExperience.get().getWorkExperienceId());
        verify(iWorkExperienceRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllWorkExperiences() {
        List<WorkExperience> workExperienceList = new ArrayList<>();
        workExperienceList.add(workExperience);

        when(iWorkExperienceRepository.findAll()).thenReturn(workExperienceList);

        List<WorkExperience> allWorkExperiences = workExperienceService.getAllWorkExperiences();

        assertEquals(1, allWorkExperiences.size());
        verify(iWorkExperienceRepository, times(1)).findAll();
    }

    @Test
    void testGetWorkExperiencesByUserId() {
        List<WorkExperience> workExperienceList = new ArrayList<>();
        workExperienceList.add(workExperience);

        when(iWorkExperienceRepository.findByUserUserId(1)).thenReturn(workExperienceList);

        List<WorkExperience> foundWorkExperiences = workExperienceService.getWorkExperiencesByUserId(1);

        assertEquals(1, foundWorkExperiences.size());
        assertEquals(workExperience.getCompanyName(), foundWorkExperiences.get(0).getCompanyName());
        verify(iWorkExperienceRepository, times(1)).findByUserUserId(1);
    }

    @Test
    void testUpdateWorkExperience() {
        when(iWorkExperienceRepository.findById(1)).thenReturn(Optional.of(workExperience));
        when(iWorkExperienceRepository.save(workExperience)).thenReturn(workExperience);

        WorkExperience updatedWorkExperience = workExperienceService.updateWorkExperience(1, workExperience);

        assertEquals(workExperience.getCompanyName(), updatedWorkExperience.getCompanyName());
        verify(iWorkExperienceRepository, times(1)).save(workExperience);
    }

    @Test
    void testDeleteWorkExperience() {
        doNothing().when(iWorkExperienceRepository).deleteById(1);

        workExperienceService.deleteWorkExperience(1);

        verify(iWorkExperienceRepository, times(1)).deleteById(1);
    }
}
