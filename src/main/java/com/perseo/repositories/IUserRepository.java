package com.perseo.repositories;

import com.perseo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    Optional<Object> findByUsername(String username);
}
