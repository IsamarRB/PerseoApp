package com.perseo.services;

import com.perseo.models.WorkExperience;
import com.perseo.repositories.IWorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class WorkExperienceService {
    @Autowired
    private IWorkExperienceRepository iWorkExperienceRepository;

    public WorkExperience createWorkExperience(WorkExperience workExperience) {return iWorkExperienceRepository.save(workExperience);}

    public Optional<WorkExperience> getWorkExperienceById(int id) {return iWorkExperienceRepository.findById(id);}

    public List<WorkExperience> getAllWorkExperiences() {return (List<WorkExperience>) iWorkExperienceRepository.findAll();}

    public List<WorkExperience> getWorkExperiencesByUserId(int userId) {return iWorkExperienceRepository.findByUserUserId(userId);}

    public WorkExperience updateWorkExperience(int id, WorkExperience workExperienceDetails) {
        Optional<WorkExperience> optionalWorkExperience = iWorkExperienceRepository.findById(id);

        if (optionalWorkExperience.isPresent()) {
            WorkExperience workExperience = optionalWorkExperience.get();
            workExperience.setCompanyName(workExperienceDetails.getCompanyName());
            workExperience.setPosition(workExperienceDetails.getPosition());
            workExperience.setYearsOfExperience(workExperienceDetails.getYearsOfExperience());
            return iWorkExperienceRepository.save(workExperience);
        } else {
            throw new RuntimeException("Work Experience not found");
        }
    }

    public void deleteWorkExperience(int id) {iWorkExperienceRepository.deleteById(id);}
}
