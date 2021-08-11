package com.czareg.simplerestservice.service;

import com.czareg.simplerestservice.controller.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    public User processUser(String login) {
        log.info("received login {}", login);
        return new User("",
                "",
                "",
                "",
                "",
                "",
                1);
    }
}