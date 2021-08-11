package com.czareg.simplerestservice.service.user;

import com.czareg.simplerestservice.client.GithubUser;
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
