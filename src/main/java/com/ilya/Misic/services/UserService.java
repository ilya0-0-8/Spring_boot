package com.ilya.Misic.services;

import com.ilya.Misic.models.User;
import com.ilya.Misic.models.enums.Role;
import com.ilya.Misic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> createUser(User user) {
        String userEmail = user.getEmail();
        if (userRepository.findByEmail(userEmail) != null)
        {
            return ResponseEntity.ok("Пользователь с таким Email уже существует!");
        }
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);

        userRepository.save(user);

        return ResponseEntity.ok("Пользователь с таким Email уже существует!");
    }
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setNumberPhone(user.getNumberPhone());
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
}