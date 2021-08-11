package com.czareg.simplerestservice.service;

import com.czareg.simplerestservice.client.GithubUser;
import com.czareg.simplerestservice.controller.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User create(GithubUser githubUser, int calculations) {
        return new User(
                githubUser.getId(),
                githubUser.getLogin(),
                githubUser.getName(),
                githubUser.getType(),
                githubUser.getAvatarUrl(),
                githubUser.getCreatedAt(),
                calculations
        );
    }
}
