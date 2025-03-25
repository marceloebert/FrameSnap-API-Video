package com.fiap.framesnap.infrastructure.video.controller.dto;

public record ThumbnailDownloadResponse(
    String fileName,
    String contentType,
    String base64Content
) {} 