package com.fiap.framesnap.crosscutting.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder();
    }

    @Test
    void encode_ShouldGenerateHashedPassword() {
        // Arrange
        String password = "test123";

        // Act
        String hashedPassword = passwordEncoder.encode(password);

        // Assert
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
        assertEquals(64, hashedPassword.length()); // SHA-256 produces 64 character hex string
        assertTrue(hashedPassword.matches("[0-9a-f]+"));
    }

    @Test
    void encode_ShouldGenerateSameHashForSamePassword() {
        // Arrange
        String password = "test123";

        // Act
        String hashedPassword1 = passwordEncoder.encode(password);
        String hashedPassword2 = passwordEncoder.encode(password);

        // Assert
        assertEquals(hashedPassword1, hashedPassword2);
    }

    @Test
    void encode_ShouldGenerateDifferentHashForDifferentPasswords() {
        // Arrange
        String password1 = "test123";
        String password2 = "test124";

        // Act
        String hashedPassword1 = passwordEncoder.encode(password1);
        String hashedPassword2 = passwordEncoder.encode(password2);

        // Assert
        assertNotEquals(hashedPassword1, hashedPassword2);
    }

    @Test
    void matches_WhenPasswordMatches_ShouldReturnTrue() {
        // Arrange
        String password = "test123";
        String hashedPassword = passwordEncoder.encode(password);

        // Act & Assert
        assertTrue(passwordEncoder.matches(password, hashedPassword));
    }

    @Test
    void matches_WhenPasswordDoesNotMatch_ShouldReturnFalse() {
        // Arrange
        String password = "test123";
        String wrongPassword = "test124";
        String hashedPassword = passwordEncoder.encode(password);

        // Act & Assert
        assertFalse(passwordEncoder.matches(wrongPassword, hashedPassword));
    }

    @Test
    void matches_WhenPasswordIsEmpty_ShouldNotMatchNonEmptyHash() {
        // Arrange
        String password = "";
        String hashedPassword = passwordEncoder.encode("test123");

        // Act & Assert
        assertFalse(passwordEncoder.matches(password, hashedPassword));
    }
} 