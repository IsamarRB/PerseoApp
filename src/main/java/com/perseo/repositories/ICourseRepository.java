package com.perseo.repositories;

import com.perseo.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface ICourseRepository extends CrudRepository<Course,Integer> {
}
