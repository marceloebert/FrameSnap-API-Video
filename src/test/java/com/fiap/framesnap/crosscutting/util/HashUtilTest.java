package com.fiap.framesnap.crosscutting.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashUtilTest {

    @Test
    void calculateSecretHash_ShouldGenerateValidHash() {
        // Arrange
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";
        String username = "testUser";

        // Act
        String hash = HashUtil.calculateSecretHash(clientId, clientSecret, username);

        // Assert
        assertNotNull(hash);
        assertTrue(hash.length() > 0);
    }

    @Test
    void calculateSecretHash_SameInputs_ShouldGenerateSameHash() {
        // Arrange
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";
        String username = "testUser";

        // Act
        String hash1 = HashUtil.calculateSecretHash(clientId, clientSecret, username);
        String hash2 = HashUtil.calculateSecretHash(clientId, clientSecret, username);

        // Assert
        assertEquals(hash1, hash2);
    }

    @Test
    void calculateSecretHash_DifferentInputs_ShouldGenerateDifferentHashes() {
        // Arrange
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";
        String username1 = "testUser1";
        String username2 = "testUser2";

        // Act
        String hash1 = HashUtil.calculateSecretHash(clientId, clientSecret, username1);
        String hash2 = HashUtil.calculateSecretHash(clientId, clientSecret, username2);

        // Assert
        assertNotEquals(hash1, hash2);
    }

    @Test
    void calculateSecretHash_WhenNullInput_ShouldThrowException() {
        // Arrange
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash(clientId, clientSecret, null)
        );
    }

    @Test
    void calculateSecretHash_WhenEmptyInput_ShouldGenerateHash() {
        // Arrange
        String clientId = "";
        String clientSecret = "";
        String username = "";

        // Act
        String hash = HashUtil.calculateSecretHash(clientId, clientSecret, username);

        // Assert
        assertNotNull(hash);
        assertTrue(hash.length() > 0);
    }
} 