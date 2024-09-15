package com.perseo.controllers;

import com.perseo.models.UserCourses;
import com.perseo.services.UserCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-courses")
public class UserCoursesController {
    @Autowired
    private UserCoursesService userCoursesService;

    @PostMapping
    public UserCourses createUserCourses(@RequestBody UserCourses userCourses) {return userCoursesService.createUserCourses(userCourses);}

    @GetMapping("/{id}")
    public ResponseEntity<UserCourses> getUserCoursesById(@PathVariable int id) {Optional<UserCourses> userCourses = userCoursesService.getUserCoursesById(id);
        return userCourses.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());}

    @GetMapping
    public List<UserCourses> getAllUserCourses() {return userCoursesService.getAllUserCourses();}

    @PutMapping("/{id}")
    public ResponseEntity<UserCourses> updateUserCourses(@PathVariable int id, @RequestBody UserCourses userCoursesDetails) {
        try {
            UserCourses updatedUserCourses = userCoursesService.updateUserCourses(id, userCoursesDetails);
            return ResponseEntity.ok(updatedUserCourses);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserCourses(@PathVariable int id) {
        userCoursesService.deleteUserCourses(id);
        return ResponseEntity.noContent().build();
    }
}
