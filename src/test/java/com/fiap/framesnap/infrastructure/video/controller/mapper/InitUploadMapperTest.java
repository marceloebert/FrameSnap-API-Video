package com.fiap.framesnap.infrastructure.video.controller.mapper;

import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import com.fiap.framesnap.infrastructure.video.controller.dto.InitUploadRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InitUploadMapperTest {

    @Test
    void toDomain_ShouldMapRequestToVideo() {
        // Arrange
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        UUID id = UUID.randomUUID();

        InitUploadRequest request = new InitUploadRequest(fileName, userEmail);

        // Act
        Video result = InitUploadMapper.toDomain(request, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(fileName, result.getFileName());
        assertEquals(userEmail, result.getUserEmail());
        assertEquals(VideoStatus.PENDING_UPLOAD, result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getPresignedUrl());
        assertNull(result.getThumbnailFileName());
        assertNull(result.getThumbnailUrl());
        assertNull(result.getProcessedAt());
    }

    @Test
    void toDomain_WithNullValues_ShouldMapToNullFields() {
        // Arrange
        UUID id = UUID.randomUUID();
        InitUploadRequest request = new InitUploadRequest(null, null);

        // Act
        Video result = InitUploadMapper.toDomain(request, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertNull(result.getFileName());
        assertNull(result.getUserEmail());
        assertEquals(VideoStatus.PENDING_UPLOAD, result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getPresignedUrl());
        assertNull(result.getThumbnailFileName());
        assertNull(result.getThumbnailUrl());
        assertNull(result.getProcessedAt());
    }

    @Test
    void toDomain_WithEmptyStrings_ShouldMapToEmptyStrings() {
        // Arrange
        UUID id = UUID.randomUUID();
        InitUploadRequest request = new InitUploadRequest("", "");

        // Act
        Video result = InitUploadMapper.toDomain(request, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("", result.getFileName());
        assertEquals("", result.getUserEmail());
        assertEquals(VideoStatus.PENDING_UPLOAD, result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getPresignedUrl());
        assertNull(result.getThumbnailFileName());
        assertNull(result.getThumbnailUrl());
        assertNull(result.getProcessedAt());
    }
} 