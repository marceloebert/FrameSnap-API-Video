package com.fiap.framesnap.crosscutting.validations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringValidatorTest {

    @Test
    void isNullOrEmpty_WhenNull_ShouldReturnTrue() {
        // Arrange
        String value = null;

        // Act
        boolean result = StringValidator.isNullOrEmpty(value);

        // Assert
        assertTrue(result);
    }

    @Test
    void isNullOrEmpty_WhenEmpty_ShouldReturnTrue() {
        // Arrange
        String value = "";

        // Act
        boolean result = StringValidator.isNullOrEmpty(value);

        // Assert
        assertTrue(result);
    }

    @Test
    void isNullOrEmpty_WhenBlank_ShouldReturnTrue() {
        // Arrange
        String value = "   ";

        // Act
        boolean result = StringValidator.isNullOrEmpty(value);

        // Assert
        assertTrue(result);
    }

    @Test
    void isNullOrEmpty_WhenNotEmpty_ShouldReturnFalse() {
        // Arrange
        String value = "teste";

        // Act
        boolean result = StringValidator.isNullOrEmpty(value);

        // Assert
        assertFalse(result);
    }

    @Test
    void isNullOrEmpty_WhenWhitespaceAndText_ShouldReturnFalse() {
        // Arrange
        String value = "  teste  ";

        // Act
        boolean result = StringValidator.isNullOrEmpty(value);

        // Assert
        assertFalse(result);
    }
} 