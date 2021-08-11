package com.czareg.simplerestservice.service.calculator;

import org.springframework.stereotype.Component;

@Component
public class CalculatorImpl implements Calculator {
    @Override
    public int calculate(int followers, int publicReposCount) {
        if (followers < 0) {
            throw new IllegalArgumentException("Followers count should never be lower then zero");
        }
        if (publicReposCount < 0) {
            throw new IllegalArgumentException("Public repositories count should never be lower then zero");
        }
        if (followers == 0) {
            //I am assuming the whole request should not fail when some user has no followers, which is actually a valid case
            //In real world I would ask API consumers or requester what do they expect in this case
            return 0;
        }
        return 6 / followers * (2 + publicReposCount);
    }
}