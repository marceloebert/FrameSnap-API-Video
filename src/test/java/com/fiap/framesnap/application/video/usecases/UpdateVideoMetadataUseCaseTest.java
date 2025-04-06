package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.crosscutting.exception.NotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateVideoMetadataUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private VideoStatusGateway videoStatusGateway;

    private UpdateVideoMetadataUseCase updateVideoMetadataUseCase;

    @BeforeEach
    void setUp() {
        updateVideoMetadataUseCase = new UpdateVideoMetadataUseCase(videoRepositoryGateway, videoStatusGateway);
    }

    @Test
    void execute_WhenVideoExists_ShouldUpdateMetadata() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String thumbnailFileName = "thumbnail.jpg";
        String thumbnailUrl = "http://example.com/thumbnail.jpg";
        String status = "COMPLETED";
        Video video = new Video(videoId, "test.mp4", "test@test.com", VideoStatus.UPLOADED, Instant.now(), "url");
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        // Act
        updateVideoMetadataUseCase.execute(videoId.toString(), thumbnailFileName, thumbnailUrl, status);

        // Assert
        verify(videoStatusGateway).updateStatus(videoId, status);
        verify(videoRepositoryGateway).save(any(Video.class));
    }

    @Test
    void execute_WhenVideoDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String thumbnailFileName = "thumbnail.jpg";
        String thumbnailUrl = "http://example.com/thumbnail.jpg";
        String status = "COMPLETED";
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            updateVideoMetadataUseCase.execute(videoId.toString(), thumbnailFileName, thumbnailUrl, status)
        );

        verify(videoStatusGateway, never()).updateStatus(any(), any());
        verify(videoRepositoryGateway, never()).save(any());
    }

    @Test
    void execute_WhenInvalidStatus_ShouldThrowIllegalArgumentException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String thumbnailFileName = "thumbnail.jpg";
        String thumbnailUrl = "http://example.com/thumbnail.jpg";
        String invalidStatus = "INVALID_STATUS";
        Video video = new Video(videoId, "test.mp4", "test@test.com", VideoStatus.UPLOADED, Instant.now(), "url");
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            updateVideoMetadataUseCase.execute(videoId.toString(), thumbnailFileName, thumbnailUrl, invalidStatus)
        );

        verify(videoStatusGateway, never()).updateStatus(any(), any());
        verify(videoRepositoryGateway, never()).save(any());
    }
} 