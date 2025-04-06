package com.fiap.framesnap.infrastructure.video.controller.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;

class VideoStatusResponseTest {

    @Test
    void constructor_ShouldCreateInstance() {
        // Arrange
        String status = "COMPLETED";
        String thumbnailFileName = "thumb.jpg";
        String thumbnailUrl = "http://example.com/thumb.jpg";
        Instant processedAt = Instant.now();

        // Act
        VideoStatusResponse response = new VideoStatusResponse(status, thumbnailFileName, thumbnailUrl, processedAt);

        // Assert
        assertEquals(status, response.status());
        assertEquals(thumbnailFileName, response.thumbnailFileName());
        assertEquals(thumbnailUrl, response.thumbnailUrl());
        assertEquals(processedAt, response.processedAt());
    }

    @Test
    void constructor_WithNullValues_ShouldCreateInstance() {
        // Arrange & Act
        VideoStatusResponse response = new VideoStatusResponse("PENDING", null, null, null);

        // Assert
        assertEquals("PENDING", response.status());
        assertNull(response.thumbnailFileName());
        assertNull(response.thumbnailUrl());
        assertNull(response.processedAt());
    }
} 