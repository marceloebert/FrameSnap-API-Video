package com.fiap.framesnap.entities.video;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class VideoTest {

    @Test
    void constructor_WithAllParameters_ShouldCreateVideo() {
        // Arrange
        UUID id = UUID.randomUUID();
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        VideoStatus status = VideoStatus.PENDING_UPLOAD;
        Instant createdAt = Instant.now();
        String presignedUrl = "https://presigned-url";
        String thumbnailFileName = "thumbnail.jpg";
        String thumbnailUrl = "https://thumbnail-url";
        Instant processedAt = Instant.now();

        // Act
        Video video = new Video(
            id,
            fileName,
            userEmail,
            status,
            createdAt,
            presignedUrl,
            thumbnailFileName,
            thumbnailUrl,
            processedAt
        );

        // Assert
        assertEquals(id, video.getId());
        assertEquals(fileName, video.getFileName());
        assertEquals(userEmail, video.getUserEmail());
        assertEquals(status, video.getStatus());
        assertEquals(createdAt, video.getCreatedAt());
        assertEquals(presignedUrl, video.getPresignedUrl());
        assertEquals(thumbnailFileName, video.getThumbnailFileName());
        assertEquals(thumbnailUrl, video.getThumbnailUrl());
        assertEquals(processedAt, video.getProcessedAt());
    }

    @Test
    void constructor_WithMinimalParameters_ShouldCreateVideo() {
        // Arrange
        UUID id = UUID.randomUUID();
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        VideoStatus status = VideoStatus.PENDING_UPLOAD;
        Instant createdAt = Instant.now();
        String presignedUrl = "https://presigned-url";

        // Act
        Video video = new Video(
            id,
            fileName,
            userEmail,
            status,
            createdAt,
            presignedUrl
        );

        // Assert
        assertEquals(id, video.getId());
        assertEquals(fileName, video.getFileName());
        assertEquals(userEmail, video.getUserEmail());
        assertEquals(status, video.getStatus());
        assertEquals(createdAt, video.getCreatedAt());
        assertEquals(presignedUrl, video.getPresignedUrl());
        assertNull(video.getThumbnailFileName());
        assertNull(video.getThumbnailUrl());
        assertNull(video.getProcessedAt());
    }

    @Test
    void constructor_NoArgs_ShouldCreateEmptyVideo() {
        // Act
        Video video = new Video();

        // Assert
        assertNull(video.getId());
        assertNull(video.getFileName());
        assertNull(video.getUserEmail());
        assertNull(video.getStatus());
        assertNull(video.getCreatedAt());
        assertNull(video.getPresignedUrl());
        assertNull(video.getThumbnailFileName());
        assertNull(video.getThumbnailUrl());
        assertNull(video.getProcessedAt());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        Video video = new Video();
        UUID id = UUID.randomUUID();
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        VideoStatus status = VideoStatus.PENDING_UPLOAD;
        Instant createdAt = Instant.now();
        String presignedUrl = "https://presigned-url";
        String thumbnailFileName = "thumbnail.jpg";
        String thumbnailUrl = "https://thumbnail-url";
        Instant processedAt = Instant.now();

        // Act
        video.setId(id);
        video.setFileName(fileName);
        video.setUserEmail(userEmail);
        video.setStatus(status);
        video.setCreatedAt(createdAt);
        video.setPresignedUrl(presignedUrl);
        video.setThumbnailFileName(thumbnailFileName);
        video.setThumbnailUrl(thumbnailUrl);
        video.setProcessedAt(processedAt);

        // Assert
        assertEquals(id, video.getId());
        assertEquals(fileName, video.getFileName());
        assertEquals(userEmail, video.getUserEmail());
        assertEquals(status, video.getStatus());
        assertEquals(createdAt, video.getCreatedAt());
        assertEquals(presignedUrl, video.getPresignedUrl());
        assertEquals(thumbnailFileName, video.getThumbnailFileName());
        assertEquals(thumbnailUrl, video.getThumbnailUrl());
        assertEquals(processedAt, video.getProcessedAt());
    }
} 