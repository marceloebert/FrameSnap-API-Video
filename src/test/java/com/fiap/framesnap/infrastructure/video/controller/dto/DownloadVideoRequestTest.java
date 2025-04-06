package com.fiap.framesnap.infrastructure.video.controller.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

class DownloadVideoRequestTest {

    @Test
    void constructor_ShouldCreateInstance() {
        // Arrange
        UUID videoId = UUID.randomUUID();

        // Act
        DownloadVideoRequest request = new DownloadVideoRequest(videoId);

        // Assert
        assertEquals(videoId, request.getVideoId());
    }

    @Test
    void setVideoId_ShouldUpdateVideoId() {
        // Arrange
        UUID initialId = UUID.randomUUID();
        DownloadVideoRequest request = new DownloadVideoRequest(initialId);
        UUID newId = UUID.randomUUID();

        // Act
        request.setVideoId(newId);

        // Assert
        assertEquals(newId, request.getVideoId());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        DownloadVideoRequest request = new DownloadVideoRequest();

        // Assert
        assertNull(request.getVideoId());
    }
} 