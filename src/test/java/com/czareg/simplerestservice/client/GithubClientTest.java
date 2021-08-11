package com.czareg.simplerestservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class requires connection to github.
 */
@SpringBootTest
class GithubClientTest {
    static final String TEST_USER_LOGIN = "czarecoo";

    @Autowired
    GithubClient githubClient;

    @Test
    void shouldReturnCorrectIdForGithubUserUsingRealGithubClient() {
        GithubUser githubUser = githubClient.getGithubUser(TEST_USER_LOGIN);

        assertNotNull(githubUser);
        assertEquals("33862699", githubUser.getId());
    }

    @Test
    void shouldReturnCorrectLoginForGithubUserUsingRealGithubClient() {
        GithubUser githubUser = githubClient.getGithubUser(TEST_USER_LOGIN);

        assertNotNull(githubUser);
        assertEquals(TEST_USER_LOGIN, githubUser.getLogin());
    }

    @Test
    void shouldReturnNullNameForGithubUserUsingRealGithubClient() {
        GithubUser githubUser = githubClient.getGithubUser(TEST_USER_LOGIN);

        assertNotNull(githubUser);
        assertNull(githubUser.getName());
    }

    @Test
    void shouldReturnCorrectAvatarUrlForGithubUserUsingRealGithubClient() {
        GithubUser githubUser = githubClient.getGithubUser(TEST_USER_LOGIN);

        assertNotNull(githubUser);
        assertEquals("https://avatars.githubusercontent.com/u/33862699?v=4", githubUser.getAvatarUrl());
    }

    @Test
    void shouldReturnCorrectDateForGithubUserUsingRealGithubClient() {
        GithubUser githubUser = githubClient.getGithubUser(TEST_USER_LOGIN);

        assertNotNull(githubUser);
        assertEquals("2017-11-21T10:00:54Z", githubUser.getCreatedAt());
    }
}