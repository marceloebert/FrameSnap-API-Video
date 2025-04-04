package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ViolationTest {

    @Test
    void constructor_WhenCreatedWithMessage_ShouldSetMessage() {
        // Arrange & Act
        String message = "Mensagem de erro";
        Violation violation = new Violation(message);

        // Assert
        assertEquals(message, violation.getMessage());
    }

    @Test
    void constructor_WhenCreatedWithoutMessage_ShouldHaveNullMessage() {
        // Arrange & Act
        Violation violation = new Violation();

        // Assert
        assertNull(violation.getMessage());
    }

    @Test
    void setMessage_ShouldUpdateMessage() {
        // Arrange
        Violation violation = new Violation("Mensagem original");
        String newMessage = "Nova mensagem";

        // Act
        violation.setMessage(newMessage);

        // Assert
        assertEquals(newMessage, violation.getMessage());
    }

    @Test
    void equals_WhenSameMessage_ShouldBeEqual() {
        // Arrange
        String message = "Mensagem de teste";
        Violation violation1 = new Violation(message);
        Violation violation2 = new Violation(message);

        // Act & Assert
        assertEquals(violation1, violation2);
        assertEquals(violation1.hashCode(), violation2.hashCode());
    }

    @Test
    void equals_WhenDifferentMessage_ShouldNotBeEqual() {
        // Arrange
        Violation violation1 = new Violation("Mensagem 1");
        Violation violation2 = new Violation("Mensagem 2");

        // Act & Assert
        assertNotEquals(violation1, violation2);
        assertNotEquals(violation1.hashCode(), violation2.hashCode());
    }

    @Test
    void equals_WhenComparedWithNull_ShouldReturnFalse() {
        // Arrange
        Violation violation = new Violation("Test");

        // Act & Assert
        assertNotEquals(null, violation);
    }

    @Test
    void equals_WhenComparedWithDifferentType_ShouldReturnFalse() {
        // Arrange
        Violation violation = new Violation("Test");
        String differentType = "Test";

        // Act & Assert
        assertNotEquals(differentType, violation);
    }

    @Test
    void toString_ShouldIncludeMessage() {
        // Arrange
        String message = "Test message";
        Violation violation = new Violation(message);

        // Act
        String result = violation.toString();

        // Assert
        assertTrue(result.contains(message));
    }
} 