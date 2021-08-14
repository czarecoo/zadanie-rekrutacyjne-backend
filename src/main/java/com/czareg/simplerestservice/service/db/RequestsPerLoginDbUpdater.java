package com.czareg.simplerestservice.service.db;

import com.czareg.simplerestservice.repository.Request;
import com.czareg.simplerestservice.repository.RequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.function.Supplier;

@Slf4j
@Component
@AllArgsConstructor
public class RequestsPerLoginDbUpdater {
    private final RequestRepository requestRepository;

    @Transactional
    public void update(String login) {
        Request request = requestRepository.findById(login)
                .orElseGet(createRequest(login));
        long requestCount = request.getRequestCount();
        long incrementedRequestCount = requestCount + 1;
        request.setRequestCount(incrementedRequestCount);
        requestRepository.save(request);
        log.info("Successfully saved request {} in db", request);
    }

    private Supplier<Request> createRequest(String login) {
        return () -> new Request(login, 0);
    }
}