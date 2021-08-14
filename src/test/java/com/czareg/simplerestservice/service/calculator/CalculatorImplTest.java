package com.czareg.simplerestservice.service.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorImplTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new CalculatorImpl();
    }

    @Test
    void shouldReturnZeroWhenFollowersIsZero() {
        float result = calculator.calculate(0, 5);

        assertEquals(0, result);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFollowersIsLowerThanZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(-5, 5));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenPublicReposCountIsLowerThanZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(5, -5));
    }

    @Test
    void shouldReturnCorrectlyCalculatedResultWhenPublicReposCountIsZero() {
        float result = calculator.calculate(2, 0);

        assertEquals(6, result);
    }

    @Test
    void shouldReturnCorrectlyCalculatedResultWhenBothParametersArePositiveNumbersAndResultIsDecimal() {
        float result = calculator.calculate(3, 5);

        assertEquals(14, result);
    }

    @Test
    void shouldReturnCorrectlyCalculatedResultWhenBothParametersArePositiveNumbersAndResultIsFloat() {
        float result = calculator.calculate(7, 38);

        assertEquals(34.28571319580078, result);
    }
}