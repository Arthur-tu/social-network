package com.socialNetwork.users.controller;

import com.socialNetwork.users.entity.User;
import com.socialNetwork.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    @Operation(summary = "Добавление пользователя")
    @PostMapping
    public String createUser(@RequestBody User user, @AuthenticationPrincipal Jwt jwt) {
        return userService.createUser(user);
    }
    @Operation(summary = "Получение пользователя")
    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return userService.getUser(id);
    }
    @Operation(summary = "Обновление пользователя")
    @PutMapping("/{id}")
    public String updateUser(@RequestBody User user, @PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        if (user.getId() != id) {
           throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return userService.updateUser(user, id);
    }
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return userService.deleteUser(id);
    }
    @Operation(summary = "Получение списка пользователей")
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
}