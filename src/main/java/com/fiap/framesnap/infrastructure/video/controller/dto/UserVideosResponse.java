package com.fiap.framesnap.infrastructure.video.controller.dto;

import java.time.Instant;
import java.util.List;

public record UserVideosResponse(
    List<VideoInfo> videos
) {
    public record VideoInfo(
        String videoId,
        String fileName,
        String status,
        String thumbnailFileName,
        String thumbnailUrl,
        String downloadUrl,
        Instant processedAt
    ) {}
} 