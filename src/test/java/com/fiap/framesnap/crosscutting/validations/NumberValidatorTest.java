package com.fiap.framesnap.crosscutting.validations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class NumberValidatorTest {

    @Test
    void isNegative_WhenNull_ShouldReturnFalse() {
        // Arrange
        BigDecimal value = null;

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertFalse(result);
    }

    @Test
    void isNegative_WhenNegative_ShouldReturnTrue() {
        // Arrange
        BigDecimal value = new BigDecimal("-1.0");

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertTrue(result);
    }

    @Test
    void isNegative_WhenZero_ShouldReturnFalse() {
        // Arrange
        BigDecimal value = BigDecimal.ZERO;

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertFalse(result);
    }

    @Test
    void isNegative_WhenPositive_ShouldReturnFalse() {
        // Arrange
        BigDecimal value = new BigDecimal("1.0");

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertFalse(result);
    }

    @Test
    void isNegative_WhenNegativeDecimal_ShouldReturnTrue() {
        // Arrange
        BigDecimal value = new BigDecimal("-0.1");

        // Act
        boolean result = NumberValidator.isNegative(value);

        // Assert
        assertTrue(result);
    }
} 