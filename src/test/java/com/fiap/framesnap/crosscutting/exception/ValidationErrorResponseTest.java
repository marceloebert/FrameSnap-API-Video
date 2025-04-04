package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationErrorResponseTest {

    @Test
    void add_ShouldAddSingleViolation() {
        // Arrange
        ValidationErrorResponse response = new ValidationErrorResponse();
        Violation violation = new Violation("Test error");

        // Act
        response.add(violation);

        // Assert
        assertEquals(1, response.getViolations().size());
        assertEquals(violation, response.getViolations().get(0));
    }

    @Test
    void addAll_ShouldAddMultipleViolations() {
        // Arrange
        ValidationErrorResponse response = new ValidationErrorResponse();
        Violation[] violations = {
            new Violation("Error 1"),
            new Violation("Error 2"),
            new Violation("Error 3")
        };

        // Act
        response.addAll(violations);

        // Assert
        assertEquals(3, response.getViolations().size());
        assertEquals("Error 1", response.getViolations().get(0).getMessage());
        assertEquals("Error 2", response.getViolations().get(1).getMessage());
        assertEquals("Error 3", response.getViolations().get(2).getMessage());
    }

    @Test
    void getViolations_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        ValidationErrorResponse response = new ValidationErrorResponse();

        // Act & Assert
        assertNotNull(response.getViolations());
        assertTrue(response.getViolations().isEmpty());
    }

    @Test
    void setViolations_ShouldReplaceExistingViolations() {
        // Arrange
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.add(new Violation("Old error"));

        // Act
        response.setViolations(java.util.Arrays.asList(
            new Violation("New error 1"),
            new Violation("New error 2")
        ));

        // Assert
        assertEquals(2, response.getViolations().size());
        assertEquals("New error 1", response.getViolations().get(0).getMessage());
        assertEquals("New error 2", response.getViolations().get(1).getMessage());
    }
} 