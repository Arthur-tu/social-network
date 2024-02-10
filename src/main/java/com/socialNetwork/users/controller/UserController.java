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
import java.util.Objects;

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
        boolean isAdmin = isAdminFromJwt(jwt);
        String preferred_username = getPreferredUsernameFromJwt(jwt);
        String email = getEmailFromJwt(jwt);
        return userService.updateUser(user, id, isAdmin, preferred_username, email);
    }
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        boolean isAdmin = isAdminFromJwt(jwt);
        String preferred_username = getPreferredUsernameFromJwt(jwt);
        String email = getEmailFromJwt(jwt);
        return userService.deleteUser(id, isAdmin, preferred_username, email);
    }
    @Operation(summary = "Получение списка пользователей")
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    private boolean isAdminFromJwt(Jwt jwt) {
        List<String> roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");
        return  roles.stream().anyMatch(role -> role.equals("users_admin"));
    }

    private String getPreferredUsernameFromJwt(Jwt jwt) {
        Object preferred_username = jwt.getClaims().get("preferred_username");
        Objects.requireNonNull(preferred_username, "Client name is missing in JWT");
        return  (String) preferred_username;
    }

    private String getEmailFromJwt(Jwt jwt) {
        Object email = jwt.getClaims().get("email");
        Objects.requireNonNull(email, "Email is missing in JWT");
        return  (String) email;
    }

}