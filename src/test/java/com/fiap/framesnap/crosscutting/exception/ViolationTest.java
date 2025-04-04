package com.fiap.framesnap.crosscutting.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ViolationTest {

    @Test
    void constructor_WhenNoArgs_ShouldCreateEmptyViolation() {
        // Act
        Violation violation = new Violation();

        // Assert
        assertNull(violation.getMessage());
    }

    @Test
    void constructor_WithMessage_ShouldCreateViolationWithMessage() {
        // Arrange
        String message = "Test error message";

        // Act
        Violation violation = new Violation(message);

        // Assert
        assertEquals(message, violation.getMessage());
    }

    @Test
    void setMessage_ShouldUpdateMessage() {
        // Arrange
        Violation violation = new Violation();
        String message = "New error message";

        // Act
        violation.setMessage(message);

        // Assert
        assertEquals(message, violation.getMessage());
    }

    @Test
    void equals_WhenSameMessage_ShouldReturnTrue() {
        // Arrange
        String message = "Test message";
        Violation violation1 = new Violation(message);
        Violation violation2 = new Violation(message);

        // Act & Assert
        assertEquals(violation1, violation2);
        assertEquals(violation1.hashCode(), violation2.hashCode());
    }

    @Test
    void equals_WhenDifferentMessage_ShouldReturnFalse() {
        // Arrange
        Violation violation1 = new Violation("Message 1");
        Violation violation2 = new Violation("Message 2");

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