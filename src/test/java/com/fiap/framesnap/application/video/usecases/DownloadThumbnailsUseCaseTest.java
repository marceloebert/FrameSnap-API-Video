package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DownloadThumbnailsUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private VideoStorageGateway videoStorageGateway;

    private DownloadThumbnailsUseCase downloadThumbnailsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        downloadThumbnailsUseCase = new DownloadThumbnailsUseCase(
                videoRepositoryGateway,
                videoStorageGateway
        );
    }

    @Test
    void execute_WhenVideoExistsAndThumbnailsAvailable_ShouldReturnThumbnailData() {
        UUID videoId = UUID.randomUUID();
        String thumbnailFileName = "thumbnails.zip";
        String thumbnailUrl = "https://example.com/thumbnails.zip";

        Video video = new Video(
                videoId,
                "test-video.mp4",
                "test@example.com",
                VideoStatus.COMPLETED,
                Instant.now(),
                "presigned-url",
                thumbnailFileName,
                thumbnailUrl,
                Instant.now()
        );

        byte[] testData = "test data".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testData);

        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));
        when(videoStorageGateway.downloadVideo(thumbnailFileName)).thenReturn(inputStream);

        var result = downloadThumbnailsUseCase.execute(videoId.toString());

        assertNotNull(result);
        assertEquals(thumbnailFileName, result.fileName());
        assertEquals("application/zip", result.contentType());
        assertNotNull(result.base64Content());
        assertTrue(result.base64Content().length() > 0);

        verify(videoStorageGateway).downloadVideo(thumbnailFileName);
    }

    @Test
    void execute_WhenVideoNotFound_ShouldThrowException() {
        UUID videoId = UUID.randomUUID();
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> downloadThumbnailsUseCase.execute(videoId.toString()));
        verify(videoStorageGateway, never()).downloadVideo(any());
    }

    @Test
    void execute_WhenThumbnailFileNameIsNull_ShouldThrowException() {
        UUID videoId = UUID.randomUUID();
        Video video = new Video(
                videoId,
                "test-video.mp4",
                "test@example.com",
                VideoStatus.PROCESSING,
                Instant.now(),
                "presigned-url"
        );

        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));

        assertThrows(RuntimeException.class, () -> downloadThumbnailsUseCase.execute(videoId.toString()));
        verify(videoStorageGateway, never()).downloadVideo(any());
    }

    @Test
    void execute_WhenThumbnailFileNameIsEmpty_ShouldThrowException() {
        String videoId = UUID.randomUUID().toString();
        Video video = new Video(
                UUID.fromString(videoId),
                "test.mp4",
                "test@example.com",
                VideoStatus.COMPLETED,
                Instant.now(),
                "presigned-url",
                null,
                "",
                Instant.now()
        );

        when(videoRepositoryGateway.findById(any(UUID.class))).thenReturn(Optional.of(video));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            downloadThumbnailsUseCase.execute(videoId);
        });

        assertEquals("Thumbnails ainda não estão disponíveis", exception.getMessage());
        verify(videoStorageGateway, never()).downloadVideo(any());
    }

    @Test
    void execute_WhenStorageGatewayThrowsException_ShouldThrowRuntimeException() {
        String videoId = UUID.randomUUID().toString();
        String thumbnailFileName = "thumbnail.zip";
        Video video = new Video(
                UUID.fromString(videoId),
                "test.mp4",
                "test@example.com",
                VideoStatus.COMPLETED,
                Instant.now(),
                "presigned-url",
                thumbnailFileName,
                "thumbnail-url",
                Instant.now()
        );

        when(videoRepositoryGateway.findById(any(UUID.class))).thenReturn(Optional.of(video));
        when(videoStorageGateway.downloadVideo(thumbnailFileName))
                .thenThrow(new RuntimeException("Erro ao fazer download"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            downloadThumbnailsUseCase.execute(videoId);
        });

        assertTrue(exception.getMessage().contains("Erro ao fazer download"));
        verify(videoStorageGateway).downloadVideo(thumbnailFileName);
    }
}
