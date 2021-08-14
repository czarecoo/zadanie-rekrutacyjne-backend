package com.czareg.simplerestservice.service.db;

import com.czareg.simplerestservice.repository.Request;
import com.czareg.simplerestservice.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RequestsPerLoginDbUpdaterTest {
    @Autowired
    RequestsPerLoginDbUpdater requestsPerLoginDbUpdater;

    @Autowired
    RequestRepository requestRepository;

    @Test
    void shouldHaveCorrectlyImplementedTransactions() throws InterruptedException {
        int numberOfThreads = 10;
        int numberOfRequestsForLogin = 100;
        int timeoutAfterSeconds = 5;
        List<Callable<Void>> callables = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            callables.add(createRequesterCallable("thread" + i, numberOfRequestsForLogin));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        executorService.invokeAll(callables, timeoutAfterSeconds, TimeUnit.SECONDS);

        Iterable<Request> requestIterable = requestRepository.findAll();
        boolean doAllRequestsHaveCorrectNumberOfRequestCount =
                StreamSupport.stream(requestIterable.spliterator(), false)
                        .allMatch(request -> request.getRequestCount() == numberOfRequestsForLogin);
        assertTrue(doAllRequestsHaveCorrectNumberOfRequestCount);
    }

    private Callable<Void> createRequesterCallable(String login, int requestCount) {
        return () -> {
            for (int i = 0; i < requestCount; i++) {
                requestsPerLoginDbUpdater.update(login);
            }
            return null;
        };
    }
}