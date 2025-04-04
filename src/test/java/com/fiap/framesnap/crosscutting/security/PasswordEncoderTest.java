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
        String password = "senha123";

        // Act
        String hashedPassword = passwordEncoder.encode(password);

        // Assert
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
        assertEquals(64, hashedPassword.length()); // SHA-256 gera hash de 64 caracteres
    }

    @Test
    void encode_SamePassword_ShouldGenerateSameHash() {
        // Arrange
        String password = "senha123";

        // Act
        String hash1 = passwordEncoder.encode(password);
        String hash2 = passwordEncoder.encode(password);

        // Assert
        assertEquals(hash1, hash2);
    }

    @Test
    void encode_DifferentPasswords_ShouldGenerateDifferentHashes() {
        // Arrange
        String password1 = "senha123";
        String password2 = "senha124";

        // Act
        String hash1 = passwordEncoder.encode(password1);
        String hash2 = passwordEncoder.encode(password2);

        // Assert
        assertNotEquals(hash1, hash2);
    }

    @Test
    void matches_WhenPasswordMatches_ShouldReturnTrue() {
        // Arrange
        String password = "senha123";
        String hashedPassword = passwordEncoder.encode(password);

        // Act
        boolean result = passwordEncoder.matches(password, hashedPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    void matches_WhenPasswordDoesNotMatch_ShouldReturnFalse() {
        // Arrange
        String password = "senha123";
        String wrongPassword = "senha124";
        String hashedPassword = passwordEncoder.encode(password);

        // Act
        boolean result = passwordEncoder.matches(wrongPassword, hashedPassword);

        // Assert
        assertFalse(result);
    }

    @Test
    void encode_EmptyPassword_ShouldGenerateHash() {
        // Arrange
        String password = "";

        // Act
        String hashedPassword = passwordEncoder.encode(password);

        // Assert
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
        assertEquals(64, hashedPassword.length());
    }
} 