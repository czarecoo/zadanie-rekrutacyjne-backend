package com.czareg.simplerestservice.controller;

import com.czareg.simplerestservice.service.UserService;
import com.czareg.simplerestservice.service.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UsersRestController {
    private final UserService userService;

    @GetMapping("/users/{login}")
    public User getUser(@PathVariable("login") String login) {
        return userService.processUser(login);
    }
}