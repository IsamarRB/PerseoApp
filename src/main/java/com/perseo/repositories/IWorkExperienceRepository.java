package com.perseo.repositories;


import com.perseo.models.WorkExperience;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IWorkExperienceRepository extends CrudRepository<WorkExperience, Integer> {
    List<WorkExperience> findByUserUserId(int userId);
}
