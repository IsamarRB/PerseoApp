package com.perseo.ServicesTest;

import com.perseo.models.Course;
import com.perseo.repositories.ICourseRepository;
import com.perseo.services.CourseService;
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

public class CourseServiceTest {
    @Mock
    private ICourseRepository iCourseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = Course.builder()
                .courseId(1)
                .title("Java Programming")
                .description("Learn Java programming from scratch.")
                .price(199.99)
                .build();
    }

    @Test
    void testGetAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);

        when(iCourseRepository.findAll()).thenReturn(courseList);

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(iCourseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        when(iCourseRepository.findById(1)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(1);

        assertTrue(result.isPresent());
        assertEquals(course.getCourseId(), result.get().getCourseId());
        verify(iCourseRepository, times(1)).findById(1);
    }

    @Test
    void testGetCourseById_NotFound() {
        when(iCourseRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.getCourseById(1);

        assertFalse(result.isPresent());
        verify(iCourseRepository, times(1)).findById(1);
    }

    @Test
    void testCreateCourse() {
        when(iCourseRepository.save(course)).thenReturn(course);

        Course result = courseService.createCourse(course);

        assertNotNull(result);
        assertEquals(course.getCourseId(), result.getCourseId());
        verify(iCourseRepository, times(1)).save(course);
    }

    @Test
    void testUpdateCourse_Success() {
        Course updatedCourse = Course.builder()
                .courseId(1)
                .title("Advanced Java Programming")
                .description("Learn advanced Java programming concepts.")
                .price(299.99)
                .build();

        when(iCourseRepository.findById(1)).thenReturn(Optional.of(course));
        when(iCourseRepository.save(course)).thenReturn(updatedCourse);

        Course result = courseService.updateCourse(1, updatedCourse);

        assertNotNull(result);
        assertEquals("Advanced Java Programming", result.getTitle());
        assertEquals(299.99, result.getPrice());
        verify(iCourseRepository, times(1)).findById(1);
        verify(iCourseRepository, times(1)).save(course);
    }

    @Test
    void testUpdateCourse_NotFound() {
        Course updatedCourse = Course.builder()
                .courseId(1)
                .title("Advanced Java Programming")
                .description("Learn advanced Java programming concepts.")
                .price(299.99)
                .build();

        when(iCourseRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseService.updateCourse(1, updatedCourse);
        });

        assertEquals("Course not found with id: 1", exception.getMessage());
        verify(iCourseRepository, times(1)).findById(1);
        verify(iCourseRepository, never()).save(any(Course.class));
    }

    @Test
    void testDeleteCourse() {
        doNothing().when(iCourseRepository).deleteById(1);

        courseService.deleteCourse(1);

        verify(iCourseRepository, times(1)).deleteById(1);
    }
}
