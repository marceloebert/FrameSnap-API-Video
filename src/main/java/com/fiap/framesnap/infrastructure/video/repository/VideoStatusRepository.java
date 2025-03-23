package com.fiap.framesnap.infrastructure.video.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;

import java.util.Optional;
import java.util.UUID;

@Repository
public class VideoStatusRepository implements VideoStatusGateway {

    private final StringRedisTemplate redisTemplate;
    private static final String REDIS_PREFIX = "video_status:";

    public VideoStatusRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void updateStatus(UUID videoId, String status) {
        redisTemplate.opsForValue().set(REDIS_PREFIX + videoId, status);
    }

    @Override
    public Optional<String> findStatusById(UUID videoId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(REDIS_PREFIX + videoId));
    }
}
