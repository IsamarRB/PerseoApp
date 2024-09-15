package com.perseo.repositories;

import com.perseo.models.UserCourses;
import org.springframework.data.repository.CrudRepository;

public interface IUserCoursesRepository extends CrudRepository<UserCourses,Integer> {
}
