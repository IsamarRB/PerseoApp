package com.perseo.controllers;

import com.perseo.models.WorkExperience;
import com.perseo.services.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/work-experience")
public class WorkExperienceController {
    @Autowired
    private WorkExperienceService workExperienceService;

    @PostMapping
    public WorkExperience createWorkExperience(@RequestBody WorkExperience workExperience) {
        return workExperienceService.createWorkExperience(workExperience);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkExperience> getWorkExperienceById(@PathVariable int id) {
        Optional<WorkExperience> workExperience = workExperienceService.getWorkExperienceById(id);
        return workExperience.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<WorkExperience> getAllWorkExperiences() { return workExperienceService.getAllWorkExperiences();}

    @GetMapping("/user/{userId}")
    public List<WorkExperience> getWorkExperiencesByUserId(@PathVariable int userId) {
        return workExperienceService.getWorkExperiencesByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkExperience> updateWorkExperience(@PathVariable int id, @RequestBody WorkExperience workExperienceDetails) {
        try {
            WorkExperience updatedWorkExperience = workExperienceService.updateWorkExperience(id, workExperienceDetails);
            return ResponseEntity.ok(updatedWorkExperience);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkExperience(@PathVariable int id) {
        workExperienceService.deleteWorkExperience(id);
        return ResponseEntity.noContent().build();
    }
}
