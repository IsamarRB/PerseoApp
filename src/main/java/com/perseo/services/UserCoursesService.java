package com.perseo.services;

import com.perseo.models.UserCourses;
import com.perseo.repositories.IUserCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCoursesService {
    @Autowired
    private IUserCoursesRepository iUserCoursesRepository;

    public UserCourses createUserCourses(UserCourses userCourses) {return iUserCoursesRepository.save(userCourses);}

    public Optional<UserCourses> getUserCoursesById(int id) {return iUserCoursesRepository.findById(id);}

    public List<UserCourses> getAllUserCourses() {return (List<UserCourses>) iUserCoursesRepository.findAll();}

    public UserCourses updateUserCourses(int id, UserCourses userCoursesDetails) {
        Optional<UserCourses> userCoursesOptional = iUserCoursesRepository.findById(id);

        if (userCoursesOptional.isPresent()) {
            UserCourses userCourses = userCoursesOptional.get();
            userCourses.setPurchaseDate(userCoursesDetails.getPurchaseDate());
            userCourses.setUser(userCoursesDetails.getUser());
            userCourses.setCourses(userCoursesDetails.getCourses());
            return iUserCoursesRepository.save(userCourses);
        } else {
            throw new RuntimeException("UserCourses not found");
        }
    }
    public void deleteUserCourses(int id) {iUserCoursesRepository.deleteById(id);
    }
}