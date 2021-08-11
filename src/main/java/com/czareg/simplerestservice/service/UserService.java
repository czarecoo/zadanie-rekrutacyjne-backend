package com.czareg.simplerestservice.service;

import com.czareg.simplerestservice.client.GithubClient;
import com.czareg.simplerestservice.client.GithubUser;
import com.czareg.simplerestservice.service.calculator.Calculator;
import com.czareg.simplerestservice.service.user.User;
import com.czareg.simplerestservice.service.user.UserFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final GithubClient githubClient;
    private final Calculator calculator;
    private final UserFactory userFactory;

    public User processUser(String login) {
        log.info("received login {}", login);
        GithubUser githubUser = githubClient.getGithubUser(login);
        log.info("collected githubUser {}", githubUser);
        int followers = githubUser.getFollowers();
        int publicReposCount = githubUser.getPublicReposCount();
        int calculations = calculator.calculate(followers, publicReposCount);
        log.info("calculations {}", calculations);
        return userFactory.create(githubUser, calculations);
    }
}