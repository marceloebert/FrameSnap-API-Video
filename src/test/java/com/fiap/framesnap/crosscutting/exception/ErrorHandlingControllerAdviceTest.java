package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorHandlingControllerAdviceTest {

    private final ErrorHandlingControllerAdvice advice = new ErrorHandlingControllerAdvice();

    @Test
    void notFoundException_ShouldReturnValidationErrorResponse() {
        // Arrange
        String errorMessage = "Recurso não encontrado";
        NotFoundException exception = new NotFoundException(errorMessage);

        // Act
        ValidationErrorResponse response = advice.notFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getViolations().size());
        assertEquals(errorMessage, response.getViolations().get(0).getMessage());
    }

    @Test
    void notFoundException_WhenNoMessage_ShouldReturnDefaultMessage() {
        // Arrange
        NotFoundException exception = new NotFoundException();

        // Act
        ValidationErrorResponse response = advice.notFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getViolations().size());
        assertEquals("Not found", response.getViolations().get(0).getMessage());
    }

    @Test
    void notFoundException_WhenNullMessage_ShouldHandleNull() {
        // Arrange
        NotFoundException exception = new NotFoundException(null);

        // Act
        ValidationErrorResponse response = advice.notFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getViolations().size());
        assertNull(response.getViolations().get(0).getMessage());
    }
} 