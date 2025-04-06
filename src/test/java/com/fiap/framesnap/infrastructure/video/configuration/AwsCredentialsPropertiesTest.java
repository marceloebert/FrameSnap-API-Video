package com.fiap.framesnap.infrastructure.video.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AwsCredentialsPropertiesTest {

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        AwsCredentialsProperties properties = new AwsCredentialsProperties();
        String accessKey = "test-access-key";
        String secretKey = "test-secret-key";
        String sessionToken = "test-session-token";
        String region = "us-east-1";

        // Act
        properties.setAccessKey(accessKey);
        properties.setSecretKey(secretKey);
        properties.setSessionToken(sessionToken);
        properties.setRegion(region);

        // Assert
        assertEquals(accessKey, properties.getAccessKey());
        assertEquals(secretKey, properties.getSecretKey());
        assertEquals(sessionToken, properties.getSessionToken());
        assertEquals(region, properties.getRegion());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        AwsCredentialsProperties properties = new AwsCredentialsProperties();

        // Assert
        assertNull(properties.getAccessKey());
        assertNull(properties.getSecretKey());
        assertNull(properties.getSessionToken());
        assertNull(properties.getRegion());
    }
} 