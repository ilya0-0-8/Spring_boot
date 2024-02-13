package com.ilya.Misic.controllers;

import com.ilya.Misic.models.User;
import com.ilya.Misic.models.enums.Role;
import com.ilya.Misic.services.UserService;
import com.ilya.Misic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/api_users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        try
        {
            List<User> users = new ArrayList<User>();
            users = userService.getAllUsers();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<String> userBlock(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно заблокирован !");
    }

    @PutMapping("/inBlock/{id}")
    public ResponseEntity<String> userInBlock(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно разблокирован !");
    }

    @PutMapping("/changeRole/{id}/{role}")
    public ResponseEntity<String> userInBlock(@PathVariable Long id, @PathVariable String role) {

        User user = userRepository.findById(id).orElse(null);
        user.getRoles().clear();

        if (role.equals("ROLE_USER")) {
            user.getRoles().add(Role.ROLE_USER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль назначена!");
        } else if (role.equals("ROLE_MANAGER")) {
            user.getRoles().add(Role.ROLE_MANAGER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль назначена!");
        } else if (role.equals("ROLE_ADMIN")) {
            user.getRoles().add(Role.ROLE_ADMIN);
            userRepository.save(user);
            return ResponseEntity.ok("Роль назначена!");
        } else {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }
    @PostMapping("/register") //запрос на регистрацию пользователя
    public ResponseEntity<String> registerUser(@RequestBody User user)
    {
        User existsUser = userRepository.findByName(user.getName());

        if (existsUser == null)
        {
            userService.createUser(user);
            return ResponseEntity.ok("Вы успешно зарегистрировались !");
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login") // ошибка при авторизаци выводится через фронт
    public ResponseEntity<User> loginUser(@RequestBody User user) {

        User existingUser = userRepository.findByName(user.getName());
        String checkPassword = existingUser.getPassword();

            if (existingUser != null)
            {
                if(existingUser.isActive())
                {
                    if (user.getPassword().equals(checkPassword))
                    {
                        if (existingUser.isUser()) {
                            return ResponseEntity.ok(existingUser);
                        }
                        if (existingUser.isAdmin()) {
                            return ResponseEntity.ok(existingUser);
                        }
                        if (existingUser.isModer()) {
                            return ResponseEntity.ok(existingUser);
                        }
                    }
                    else
                    {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                else
                {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok("Информация успешно обновлена");
        } else {
            return ResponseEntity.ok("Пользователь не найден");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Пользователь успешно удалён");
    }

}
