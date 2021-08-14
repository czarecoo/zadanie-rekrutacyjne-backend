package com.czareg.simplerestservice.service;

import com.czareg.simplerestservice.client.GithubClient;
import com.czareg.simplerestservice.client.GithubUser;
import com.czareg.simplerestservice.repository.Request;
import com.czareg.simplerestservice.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceIT {
    static final String TEST_USER_LOGIN = "czarecoo";

    @MockBean
    GithubClient githubClient;

    @Autowired
    UserService userService;

    @Autowired
    RequestRepository requestRepository;

    @BeforeEach
    void setUp() {
        GithubUser githubUser = new GithubUser("1", TEST_USER_LOGIN, null, null, null, null, 5, 33);
        when(githubClient.getGithubUser(any())).thenReturn(githubUser);
    }

    @Test
    void shouldHaveCorrectlyImplementedTransactions() throws InterruptedException {
        int numberOfThreads = 10;
        int numberOfRequestsForLoginPerThread = 100;
        int timeoutAfterSeconds = 5;
        List<Callable<Void>> callables = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            callables.add(createRequesterCallable(TEST_USER_LOGIN, numberOfRequestsForLoginPerThread));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        executorService.invokeAll(callables, timeoutAfterSeconds, TimeUnit.SECONDS);

        Optional<Request> optionalRequest = requestRepository.findById(TEST_USER_LOGIN);
        assertTrue(optionalRequest.isPresent());
        Request request = optionalRequest.get();
        int expectedRequestCount = numberOfThreads * numberOfRequestsForLoginPerThread;
        assertEquals(expectedRequestCount, request.getRequestCount());
    }

    private Callable<Void> createRequesterCallable(String login, int requestCount) {
        return () -> {
            for (int i = 0; i < requestCount; i++) {
                userService.processUser(login);
            }
            return null;
        };
    }
}
