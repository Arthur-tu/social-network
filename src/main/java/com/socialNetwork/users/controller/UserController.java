package com.socialNetwork.users.controller;

import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public String updateUser(@RequestBody User user, @PathVariable int id) {
        if (user.getId() != id) {
           throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
}