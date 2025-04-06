package com.fiap.framesnap.infrastructure.video.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RedisPropertiesTest {

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        RedisProperties properties = new RedisProperties();
        String host = "localhost";
        int port = 6379;
        String username = "default";
        String password = "test-password";

        // Act
        properties.setHost(host);
        properties.setPort(port);
        properties.setUsername(username);
        properties.setPassword(password);

        // Assert
        assertEquals(host, properties.getHost());
        assertEquals(port, properties.getPort());
        assertEquals(username, properties.getUsername());
        assertEquals(password, properties.getPassword());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        RedisProperties properties = new RedisProperties();

        // Assert
        assertNull(properties.getHost());
        assertEquals(0, properties.getPort());
        assertNull(properties.getUsername());
        assertNull(properties.getPassword());
    }
} 