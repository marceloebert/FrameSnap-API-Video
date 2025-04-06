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
public class UpdateVideoStatusUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private VideoStatusGateway videoStatusGateway;

    private UpdateVideoStatusUseCase updateVideoStatusUseCase;

    @BeforeEach
    void setUp() {
        updateVideoStatusUseCase = new UpdateVideoStatusUseCase(videoRepositoryGateway, videoStatusGateway);
    }

    @Test
    void execute_WhenVideoExists_ShouldUpdateStatus() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String status = "PROCESSED";
        Video video = new Video(videoId, "test.mp4", "test@test.com", VideoStatus.UPLOADED, Instant.now(), "url");
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        // Act
        updateVideoStatusUseCase.execute(videoId.toString(), status);

        // Assert
        verify(videoStatusGateway).updateStatus(videoId, status);
        verify(videoRepositoryGateway).save(any(Video.class));
    }

    @Test
    void execute_WhenVideoDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String status = "PROCESSED";
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> 
            updateVideoStatusUseCase.execute(videoId.toString(), status)
        );

        verify(videoStatusGateway, never()).updateStatus(any(), any());
        verify(videoRepositoryGateway, never()).save(any());
    }

    @Test
    void execute_WhenInvalidStatus_ShouldThrowIllegalArgumentException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String invalidStatus = "INVALID_STATUS";
        Video video = new Video(videoId, "test.mp4", "test@test.com", VideoStatus.UPLOADED, Instant.now(), "url");
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            updateVideoStatusUseCase.execute(videoId.toString(), invalidStatus)
        );

        verify(videoStatusGateway, never()).updateStatus(any(), any());
        verify(videoRepositoryGateway, never()).save(any());
    }
} 