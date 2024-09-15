package com.perseo.ControllerTest;

import com.perseo.controllers.CourseController;
import com.perseo.models.Course;
import com.perseo.services.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    public CourseControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for getting all courses
    @Test
    void testGetAllCourses() {
        when(courseService.getAllCourses()).thenReturn(Arrays.asList(
                new Course(1, "Course 1", "Description 1", 100.0),
                new Course(2, "Course 2", "Description 2", 150.0)
        ));

        ResponseEntity<List<Course>> response = courseController.getAllCourses();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    // Test for getting a course by ID
    @Test
    void testGetCourseById() {
        Course mockCourse = new Course(1, "Course 1", "Description 1", 100.0);
        when(courseService.getCourseById(1)).thenReturn(Optional.of(mockCourse));

        ResponseEntity<Course> response = courseController.getCourseById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCourse, response.getBody());
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseService.getCourseById(1)).thenReturn(Optional.empty());

        ResponseEntity<Course> response = courseController.getCourseById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for creating a course
    @Test
    void testCreateCourse() {
        Course mockCourse = new Course(1, "Course 1", "Description 1", 100.0);
        when(courseService.createCourse(mockCourse)).thenReturn(mockCourse);

        ResponseEntity<Course> response = courseController.createCourse(mockCourse);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockCourse, response.getBody());
    }

    // Test for updating a course
    @Test
    void testUpdateCourse() {
        Course mockCourse = new Course(1, "Updated Title", "Updated Description", 200.0);
        when(courseService.updateCourse(eq(1), any(Course.class))).thenReturn(mockCourse);

        ResponseEntity<Course> response = courseController.updateCourse(1, mockCourse);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCourse, response.getBody());
    }

    @Test
    void testUpdateCourse_NotFound() {
        when(courseService.updateCourse(eq(1), any(Course.class))).thenThrow(new RuntimeException());

        ResponseEntity<Course> response = courseController.updateCourse(1, new Course());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for deleting a course
    @Test
    void testDeleteCourse() {
        doNothing().when(courseService).deleteCourse(1);

        ResponseEntity<Void> response = courseController.deleteCourse(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteCourse(1);
    }
}
