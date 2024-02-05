package com.trueman.KP_Vacancy.repositories;

import com.trueman.KP_Vacancy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
    User findByName(String name);
    User findByPassword(String password);
    User findByEmail(String email);

    List<User> findAll();
}

