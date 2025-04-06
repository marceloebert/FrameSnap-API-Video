package com.fiap.framesnap.infrastructure.video.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class S3PropertiesTest {

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        S3Properties properties = new S3Properties();
        String endpoint = "http://localhost:4566";
        String bucket = "test-bucket";
        String region = "us-east-1";

        // Act
        properties.setEndpoint(endpoint);
        properties.setBucket(bucket);
        properties.setRegion(region);

        // Assert
        assertEquals(endpoint, properties.getEndpoint());
        assertEquals(bucket, properties.getBucket());
        assertEquals(region, properties.getRegion());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        S3Properties properties = new S3Properties();

        // Assert
        assertNull(properties.getEndpoint());
        assertNull(properties.getBucket());
        assertNull(properties.getRegion());
    }
} 