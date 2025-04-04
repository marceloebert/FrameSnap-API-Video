package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorHandlingControllerAdviceTest {

    private ErrorHandlingControllerAdvice errorHandlingControllerAdvice;

    @BeforeEach
    void setUp() {
        errorHandlingControllerAdvice = new ErrorHandlingControllerAdvice();
    }

    @Test
    void notFoundException_WithCustomMessage_ShouldReturnValidationErrorResponse() {
        // Arrange
        String errorMessage = "Resource not found";
        NotFoundException exception = new NotFoundException(errorMessage);

        // Act
        ValidationErrorResponse response = errorHandlingControllerAdvice.notFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getViolations().size());
        assertEquals(errorMessage, response.getViolations().get(0).getMessage());
    }

    @Test
    void notFoundException_WithDefaultMessage_ShouldReturnValidationErrorResponse() {
        // Arrange
        NotFoundException exception = new NotFoundException();

        // Act
        ValidationErrorResponse response = errorHandlingControllerAdvice.notFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getViolations().size());
        assertEquals("Not found", response.getViolations().get(0).getMessage());
    }

    @Test
    void notFoundException_ShouldCreateNewViolation() {
        // Arrange
        String errorMessage = "Custom error message";
        NotFoundException exception = new NotFoundException(errorMessage);

        // Act
        ValidationErrorResponse response = errorHandlingControllerAdvice.notFoundException(exception);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getViolations());
        assertEquals(1, response.getViolations().size());
        
        Violation violation = response.getViolations().get(0);
        assertNotNull(violation);
        assertEquals(errorMessage, violation.getMessage());
    }
} 