package com.perseo.services;

import com.perseo.models.Course;
import com.perseo.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private ICourseRepository iCourseRepository;

    public List<Course> getAllCourses() {return (List<Course>) iCourseRepository.findAll(); }

    public Optional<Course> getCourseById(int courseId) {return iCourseRepository.findById(courseId);}

    public Course createCourse(Course course) { return iCourseRepository.save(course);}

    public Course updateCourse(int courseId, Course updatedCourse) {
        return iCourseRepository.findById(courseId).map(course -> {
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            course.setPrice(updatedCourse.getPrice());
            return iCourseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    public void deleteCourse(int courseId) {iCourseRepository.deleteById(courseId);
    }
}
