package com.fiap.framesnap.infrastructure.video.controller.mapper;

import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import com.fiap.framesnap.infrastructure.video.controller.dto.InitUploadRequest;

import java.time.Instant;
import java.util.UUID;

public class InitUploadMapper {

    public static Video toDomain(InitUploadRequest request, UUID id) {
        return new Video(
                id,
                request.fileName(),
                request.userEmail(),
                VideoStatus.PENDING_UPLOAD,
                Instant.now(),
                null // presignedUrl será definida depois
        );
    }
}
