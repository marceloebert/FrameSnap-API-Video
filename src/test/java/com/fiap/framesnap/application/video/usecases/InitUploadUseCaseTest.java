package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InitUploadUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private VideoStorageGateway videoStorageGateway;

    @Mock
    private VideoStatusGateway videoStatusGateway;

    private InitUploadUseCase initUploadUseCase;

    @BeforeEach
    void setUp() {
        initUploadUseCase = new InitUploadUseCase(
                videoRepositoryGateway,
                videoStorageGateway,
                videoStatusGateway
        );
    }

    @Test
    void execute_ShouldCreateVideoAndReturnPresignedUrl() {
        // Arrange
        String fileName = "test-video.mp4";
        String userEmail = "test@example.com";
        String presignedUrl = "https://example.com/presigned-url";
        UUID videoId = UUID.randomUUID();

        Video video = new Video(
                videoId,
                fileName,
                userEmail,
                VideoStatus.PENDING_UPLOAD,
                Instant.now(),
                presignedUrl
        );

        doNothing().when(videoRepositoryGateway).save(any(Video.class));
        when(videoStorageGateway.generatePresignedUploadUrl(anyString())).thenReturn(presignedUrl);
        doNothing().when(videoStatusGateway).updateStatus(any(UUID.class), anyString());

        // Act
        InitUploadUseCase.InitUploadOutput result = initUploadUseCase.execute(fileName, userEmail);

        // Assert
        assertNotNull(result);
        assertEquals(presignedUrl, result.presignedUrl());
        assertNotNull(result.videoId());
        
        verify(videoRepositoryGateway).save(argThat(savedVideo -> 
            savedVideo.getFileName().equals(fileName) &&
            savedVideo.getUserEmail().equals(userEmail) &&
            savedVideo.getStatus() == VideoStatus.PENDING_UPLOAD &&
            savedVideo.getThumbnailFileName() == null &&
            savedVideo.getThumbnailUrl() == null
        ));
        verify(videoStorageGateway).generatePresignedUploadUrl(eq(fileName));
        verify(videoStatusGateway).updateStatus(eq(videoId), eq(VideoStatus.PENDING_UPLOAD.toString()));
    }

    @Test
    void execute_WhenExceptionOccurs_ShouldThrowRuntimeException() {
        // Arrange
        String fileName = "test-video.mp4";
        String userEmail = "test@example.com";

        doThrow(new RuntimeException("Test error"))
                .when(videoStorageGateway).generatePresignedUploadUrl(anyString());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> initUploadUseCase.execute(fileName, userEmail));
        assertTrue(exception.getMessage().contains("Erro ao inicializar upload"));
        verify(videoRepositoryGateway, never()).save(any(Video.class));
        verify(videoStatusGateway, never()).updateStatus(any(), any());
    }
} 