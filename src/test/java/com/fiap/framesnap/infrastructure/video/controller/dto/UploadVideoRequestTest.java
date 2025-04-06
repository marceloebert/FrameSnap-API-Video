package com.fiap.framesnap.infrastructure.video.controller.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UploadVideoRequestTest {

    @Test
    void constructor_ShouldCreateInstance() {
        // Arrange & Act
        UploadVideoRequest request = new UploadVideoRequest("test.mp4", "user@test.com");

        // Assert
        assertEquals("test.mp4", request.getFileName());
        assertEquals("user@test.com", request.getUserEmail());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        UploadVideoRequest request = new UploadVideoRequest("test.mp4", "user@test.com");

        // Act
        request.setFileName("new.mp4");
        request.setUserEmail("new@test.com");

        // Assert
        assertEquals("new.mp4", request.getFileName());
        assertEquals("new@test.com", request.getUserEmail());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        UploadVideoRequest request = new UploadVideoRequest();

        // Assert
        assertNull(request.getFileName());
        assertNull(request.getUserEmail());
    }
} 