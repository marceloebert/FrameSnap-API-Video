package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetVideoStatusUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private VideoStatusGateway videoStatusGateway;

    private GetVideoStatusUseCase getVideoStatusUseCase;

    @BeforeEach
    void setUp() {
        getVideoStatusUseCase = new GetVideoStatusUseCase(videoRepositoryGateway, videoStatusGateway);
    }

    @Test
    void execute_WhenVideoExistsInRedisAndDynamoDB_ShouldReturnVideoWithRedisStatus() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String redisStatus = VideoStatus.PROCESSING.toString();
        Video video = new Video(
            videoId,
            "test.mp4",
            "test@example.com",
            VideoStatus.UPLOADING,
            Instant.now(),
            "presigned-url"
        );

        when(videoStatusGateway.findStatusById(videoId)).thenReturn(Optional.of(redisStatus));
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        // Act
        Video result = getVideoStatusUseCase.execute(videoId.toString());

        // Assert
        assertNotNull(result);
        assertEquals(VideoStatus.PROCESSING, result.getStatus());
        verify(videoStatusGateway).findStatusById(videoId);
        verify(videoRepositoryGateway).findById(videoId);
    }

    @Test
    void execute_WhenVideoNotInRedisButInDynamoDB_ShouldReturnVideoWithDynamoDBStatus() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        Video video = new Video(
            videoId,
            "test.mp4",
            "test@example.com",
            VideoStatus.COMPLETED,
            Instant.now(),
            "presigned-url"
        );

        when(videoStatusGateway.findStatusById(videoId)).thenReturn(Optional.empty());
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        // Act
        Video result = getVideoStatusUseCase.execute(videoId.toString());

        // Assert
        assertNotNull(result);
        assertEquals(VideoStatus.COMPLETED, result.getStatus());
        verify(videoStatusGateway).findStatusById(videoId);
        verify(videoRepositoryGateway).findById(videoId);
    }

    @Test
    void execute_WhenVideoNotFound_ShouldThrowException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        when(videoStatusGateway.findStatusById(videoId)).thenReturn(Optional.empty());
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getVideoStatusUseCase.execute(videoId.toString());
        });

        assertEquals("Vídeo não encontrado", exception.getMessage());
        verify(videoStatusGateway).findStatusById(videoId);
        verify(videoRepositoryGateway).findById(videoId);
    }

    @Test
    void execute_WhenInvalidUUID_ShouldThrowException() {
        // Arrange
        String invalidUUID = "invalid-uuid";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            getVideoStatusUseCase.execute(invalidUUID);
        });

        verify(videoStatusGateway, never()).findStatusById(any());
        verify(videoRepositoryGateway, never()).findById(any());
    }
} 