package com.socialNetwork.users.controller;

import com.socialNetwork.users.entity.Sub;
import com.socialNetwork.users.service.SubService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    String follow(@RequestBody Sub sub) {
        return subService.follow(sub);
    }

    @GetMapping(path = "/{id}")
    Sub getSub(@PathVariable int id) {
        return subService.getSub(id);
    }

    @PutMapping("/{id}")
    String updateSub(@RequestBody Sub sub, @PathVariable int id) {
        if (sub.getId() != id) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return subService.updateSub(sub, id);
    }

    @DeleteMapping("/{id}")
    String unfollow(@PathVariable int id) {
        return subService.unfollow(id);
    }

    @GetMapping
    List<Sub> getAllSubss() {
        return subService.getAllSubs();
    }


}
