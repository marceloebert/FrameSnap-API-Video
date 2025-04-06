package com.fiap.framesnap.infrastructure.video.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SQSPropertiesTest {

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        SQSProperties properties = new SQSProperties();
        String queueUrl = "http://localhost:4566/000000000000/test-queue";

        // Act
        properties.setQueueUrl(queueUrl);

        // Assert
        assertEquals(queueUrl, properties.getQueueUrl());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        SQSProperties properties = new SQSProperties();

        // Assert
        assertNull(properties.getQueueUrl());
    }
} 