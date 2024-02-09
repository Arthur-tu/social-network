package com.socialNetwork.users.controller;

import com.socialNetwork.users.entity.Sub;
import com.socialNetwork.users.service.SubService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/subs")
public class SubController {
    private final SubService subService;

    public SubController(SubService subService) {
        this.subService = subService;
    }
    @Operation(summary = "Создание подписки")
    @PostMapping
    public String follow(@RequestBody Sub sub, @AuthenticationPrincipal Jwt jwt) {
        return subService.follow(sub);
    }
    @Operation(summary = "Получение подписки")
    @GetMapping(path = "/{id}")
    public Sub getSub(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return subService.getSub(id);
    }
    @Operation(summary = "Обновление подписки")
    @PutMapping("/{id}")
    public String updateSub(@RequestBody Sub sub, @PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        if (sub.getId() != id) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return subService.updateSub(sub, id);
    }
    @Operation(summary = "Удаление подписки")
    @DeleteMapping("/{id}")
    public String unfollow(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return subService.unfollow(id);
    }
    @Operation(summary = "Получение списка подписок")
    @GetMapping
    public List<Sub> getAllSubss() {
        return subService.getAllSubs();
    }
}
