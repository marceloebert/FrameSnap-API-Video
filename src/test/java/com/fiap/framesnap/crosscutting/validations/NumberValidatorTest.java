package com.fiap.framesnap.crosscutting.validations;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class NumberValidatorTest {

    @Test
    void isNegative_WhenNull_ShouldReturnFalse() {
        assertFalse(NumberValidator.isNegative(null));
    }

    @Test
    void isNegative_WhenNegative_ShouldReturnTrue() {
        assertTrue(NumberValidator.isNegative(new BigDecimal("-1")));
        assertTrue(NumberValidator.isNegative(new BigDecimal("-0.1")));
        assertTrue(NumberValidator.isNegative(new BigDecimal("-999999.99")));
    }

    @Test
    void isNegative_WhenZero_ShouldReturnFalse() {
        assertFalse(NumberValidator.isNegative(BigDecimal.ZERO));
        assertFalse(NumberValidator.isNegative(new BigDecimal("0.00")));
    }

    @Test
    void isNegative_WhenPositive_ShouldReturnFalse() {
        assertFalse(NumberValidator.isNegative(BigDecimal.ONE));
        assertFalse(NumberValidator.isNegative(new BigDecimal("0.1")));
        assertFalse(NumberValidator.isNegative(new BigDecimal("999999.99")));
    }
} 