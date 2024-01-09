package ru.yandex.practicum.catsgram.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;


import java.util.List;



@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/email")
    public User findByEmail(@PathVariable("email") String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
        return userService.createUser(user);
    }

    @PutMapping(value = "/users")
    public User setUser(@RequestBody User user) throws InvalidEmailException {
        return userService.setUser(user);
    }
}
