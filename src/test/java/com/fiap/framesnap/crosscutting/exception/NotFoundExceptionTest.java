package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotFoundExceptionTest {

    @Test
    void constructor_WhenNoArgs_ShouldSetDefaultMessage() {
        // Arrange & Act
        NotFoundException exception = new NotFoundException();

        // Assert
        assertEquals("Not found", exception.getMessage());
    }

    @Test
    void constructor_WhenCustomMessage_ShouldSetCustomMessage() {
        // Arrange
        String customMessage = "Recurso não encontrado";

        // Act
        NotFoundException exception = new NotFoundException(customMessage);

        // Assert
        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void getMessage_ShouldReturnMessage() {
        // Arrange
        String message = "Mensagem personalizada";
        NotFoundException exception = new NotFoundException(message);

        // Act
        String result = exception.getMessage();

        // Assert
        assertEquals(message, result);
    }

    @Test
    void constructor_WhenNullMessage_ShouldAcceptNull() {
        // Act
        NotFoundException exception = new NotFoundException(null);

        // Assert
        assertNull(exception.getMessage());
    }

    @Test
    void constructor_WhenEmptyMessage_ShouldAcceptEmptyString() {
        // Act
        NotFoundException exception = new NotFoundException("");

        // Assert
        assertEquals("", exception.getMessage());
    }
} 