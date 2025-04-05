// Arquivo: VideoStatusRepositoryTest.java
package com.fiap.framesnap.infrastructure.video.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoStatusRepositoryTest {

    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;
    private VideoStatusRepository repository;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(StringRedisTemplate.class);
        valueOps = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        repository = new VideoStatusRepository(redisTemplate);
    }

    @Test
    void testUpdateStatus_shouldStoreStatusInRedis() {
        UUID videoId = UUID.randomUUID();
        String status = "COMPLETED";

        repository.updateStatus(videoId, status);

        verify(valueOps).set("video_status:" + videoId, status);
    }

    @Test
    void testFindStatusById_shouldReturnStatusWhenExists() {
        UUID videoId = UUID.randomUUID();
        String expectedStatus = "PROCESSING";
        when(valueOps.get("video_status:" + videoId)).thenReturn(expectedStatus);

        Optional<String> result = repository.findStatusById(videoId);

        assertTrue(result.isPresent());
        assertEquals(expectedStatus, result.get());
    }

    @Test
    void testFindStatusById_shouldReturnEmptyWhenNotExists() {
        UUID videoId = UUID.randomUUID();
        when(valueOps.get("video_status:" + videoId)).thenReturn(null);

        Optional<String> result = repository.findStatusById(videoId);

        assertFalse(result.isPresent());
    }
}
