package com.fiap.framesnap.infrastructure.video.controller.dto;

import java.time.Instant;

public record VideoStatusResponse(
    String status,
    String thumbnailFileName,
    String thumbnailUrl,
    Instant processedAt
) {} 