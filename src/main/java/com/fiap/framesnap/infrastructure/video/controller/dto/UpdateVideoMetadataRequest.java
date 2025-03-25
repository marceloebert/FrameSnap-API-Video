package com.fiap.framesnap.infrastructure.video.controller.dto;

public record UpdateVideoMetadataRequest(
    String thumbnailFileName,
    String thumbnailUrl,
    String status
) {} 