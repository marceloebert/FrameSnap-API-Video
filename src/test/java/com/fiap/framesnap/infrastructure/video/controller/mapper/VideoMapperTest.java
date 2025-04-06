// Arquivo: VideoMapperTest.java
package com.fiap.framesnap.infrastructure.video.controller.mapper;

import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import com.fiap.framesnap.infrastructure.video.controller.dto.UploadVideoResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VideoMapperTest {

    @Test
    void testToUploadResponse_shouldMapAllFieldsCorrectly() {
        UUID videoId = UUID.randomUUID();
        Video video = new Video(
                videoId,
                "video.mp4",
                "user@email.com",
                VideoStatus.COMPLETED,
                Instant.now(),
                "http://image.com/thumb.jpg"
        );

        UploadVideoResponse response = VideoMapper.toUploadResponse(video);

        assertNotNull(response);
        assertEquals(videoId, response.getId());
        assertEquals("video.mp4", response.getFileName());
        assertEquals("COMPLETED", response.getStatus());
    }

    @Test
    void testToUploadResponse_whenVideoIsNull_shouldReturnNull() {
        UploadVideoResponse response = VideoMapper.toUploadResponse(null);
        assertNull(response);
    }
}
