package com.perseo.services;

import com.perseo.repositories.IUserRepository;
import com.perseo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;

    public User createUser(User user) {return iUserRepository.save(user);}

    public Optional<User> getUserById(int id) {return iUserRepository.findById(id);}

    public List<User> getAllUsers() {return (List<User>) iUserRepository.findAll();}

    public User updateUser(int id, User userDetails) {
        Optional<User> userOptional = iUserRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setWorkExperiences(userDetails.getWorkExperiences());
            user.setPurchasedCourses(userDetails.getPurchasedCourses());
            return iUserRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(int id) {
        iUserRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }
}
