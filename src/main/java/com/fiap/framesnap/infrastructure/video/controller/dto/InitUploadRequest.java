package com.fiap.framesnap.infrastructure.video.controller.dto;

public record InitUploadRequest(
        String fileName,
        String userEmail
) {}
