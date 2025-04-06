package com.fiap.framesnap.infrastructure.video.gateway;

import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SQSVideoQueueGatewayTest {

    @Mock
    private SqsClient sqsClient;

    private SQSVideoQueueGateway sqsVideoQueueGateway;
    private final String queueUrl = "http://example.com/queue";

    @BeforeEach
    void setUp() {
        sqsVideoQueueGateway = new SQSVideoQueueGateway(sqsClient, queueUrl);
    }

    @Test
    void sendMessage_ShouldSendMessageToQueue() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        Video video = new Video(videoId, "test.mp4", "test@test.com", VideoStatus.UPLOADED, Instant.now(), "url");
        
        when(sqsClient.sendMessage(any(SendMessageRequest.class)))
            .thenReturn(SendMessageResponse.builder().build());

        // Act
        sqsVideoQueueGateway.sendMessage(video);

        // Assert
        verify(sqsClient).sendMessage(any(SendMessageRequest.class));
    }
} 