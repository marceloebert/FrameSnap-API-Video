package com.fiap.framesnap.infrastructure.video.controller.dto;

public record InitUploadResponse(
        String videoId,
        String presignedUrl
) {}
