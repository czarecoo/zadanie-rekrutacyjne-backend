package com.czareg.simplerestservice.service;

import com.czareg.simplerestservice.client.GithubClient;
import com.czareg.simplerestservice.client.GithubUser;
import com.czareg.simplerestservice.repository.Request;
import com.czareg.simplerestservice.repository.RequestRepository;
import com.czareg.simplerestservice.service.user.User;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    static final String TEST_USER_LOGIN = "czarecoo";

    @MockBean
    GithubClient githubClient;

    @Autowired
    UserService userService;

    @Autowired
    RequestRepository requestRepository;

    @BeforeEach
    void setUp() {
        requestRepository.deleteAll();
    }

    @Test
    void shouldNotHaveRequestForLoginWhenItWasNeverCalled() {
        Optional<Request> optionalRequest = requestRepository.findById(TEST_USER_LOGIN);
        assertFalse(optionalRequest.isPresent());
    }

    @Test
    void shouldHaveRequestForLoginWithCountOneWhenItWasCalledOnce() {
        GithubUser githubUser = new GithubUser("1", TEST_USER_LOGIN, null, null, null, null, 5, 33);
        when(githubClient.getGithubUser(any())).thenReturn(githubUser);

        userService.processUser(TEST_USER_LOGIN);

        Optional<Request> optionalRequest = requestRepository.findById(TEST_USER_LOGIN);
        assertTrue(optionalRequest.isPresent());
        Request request = optionalRequest.get();
        assertEquals(TEST_USER_LOGIN, request.getLogin());
        assertEquals(1, request.getRequestCount());
    }

    @Test
    void shouldHaveRequestForLoginWithCountTwoWhenItWasCalledTwice() {
        GithubUser githubUser = new GithubUser("1", TEST_USER_LOGIN, null, null, null, null, 5, 33);
        when(githubClient.getGithubUser(any())).thenReturn(githubUser);

        userService.processUser(TEST_USER_LOGIN);
        userService.processUser(TEST_USER_LOGIN);

        Optional<Request> optionalRequest = requestRepository.findById(TEST_USER_LOGIN);
        assertTrue(optionalRequest.isPresent());
        Request request = optionalRequest.get();
        assertEquals(TEST_USER_LOGIN, request.getLogin());
        assertEquals(2, request.getRequestCount());
    }

    @Test
    void shouldReturnUserWithCorrectCalculation() {
        GithubUser githubUser = new GithubUser("1", TEST_USER_LOGIN, null, null, null, null, 5, 33);
        when(githubClient.getGithubUser(any())).thenReturn(githubUser);

        User user = userService.processUser(TEST_USER_LOGIN);

        assertNotNull(user);
        assertEquals(42, user.getCalculations());
    }

    @Test
    void shouldSaveRequestForLoginEvenWhenGithubCallFails() {
        when(githubClient.getGithubUser(any())).thenThrow(FeignException.class);

        try {
            userService.processUser(TEST_USER_LOGIN);
        } catch (FeignException e) {
            //ignoring for test purposes
        }

        Optional<Request> optionalRequest = requestRepository.findById(TEST_USER_LOGIN);
        assertTrue(optionalRequest.isPresent());
        Request request = optionalRequest.get();
        assertEquals(TEST_USER_LOGIN, request.getLogin());
        assertEquals(1, request.getRequestCount());
    }
}