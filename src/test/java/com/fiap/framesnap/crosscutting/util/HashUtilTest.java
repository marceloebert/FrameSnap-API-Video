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
    void calculateSecretHash_WhenNullClientId_ShouldThrowException() {
        // Arrange
        String clientSecret = "testClientSecret";
        String username = "testUser";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash(null, clientSecret, username)
        );
    }

    @Test
    void calculateSecretHash_WhenNullClientSecret_ShouldThrowException() {
        // Arrange
        String clientId = "testClientId";
        String username = "testUser";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash(clientId, null, username)
        );
    }

    @Test
    void calculateSecretHash_WhenNullUsername_ShouldThrowException() {
        // Arrange
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash(clientId, clientSecret, null)
        );
    }

    @Test
    void calculateSecretHash_WhenEmptyClientId_ShouldThrowException() {
        // Arrange
        String clientSecret = "testClientSecret";
        String username = "testUser";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash("", clientSecret, username)
        );
    }

    @Test
    void calculateSecretHash_WhenEmptyClientSecret_ShouldThrowException() {
        // Arrange
        String clientId = "testClientId";
        String username = "testUser";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash(clientId, "", username)
        );
    }

    @Test
    void calculateSecretHash_WhenEmptyUsername_ShouldThrowException() {
        // Arrange
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            HashUtil.calculateSecretHash(clientId, clientSecret, "")
        );
    }
} 