package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoQueueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sqs.SqsClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SQSConfigTest {

    private SQSConfig sqsConfig;
    private SQSProperties sqsProperties;

    @BeforeEach
    void setUp() {
        sqsProperties = new SQSProperties();
        sqsProperties.setQueueUrl("http://localhost:4566/000000000000/test-queue");

        sqsConfig = new SQSConfig();
    }

    @Test
    void sqsClient_ShouldCreateClientWithCorrectConfiguration() {
        // Act
        SqsClient client = sqsConfig.sqsClient();

        // Assert
        assertNotNull(client);
    }

    @Test
    void videoQueueGateway_ShouldCreateGatewayWithCorrectConfiguration() {
        // Arrange
        SqsClient sqsClient = mock(SqsClient.class);

        // Act
        VideoQueueGateway gateway = sqsConfig.videoQueueGateway(sqsClient, sqsProperties);

        // Assert
        assertNotNull(gateway);
    }
} 