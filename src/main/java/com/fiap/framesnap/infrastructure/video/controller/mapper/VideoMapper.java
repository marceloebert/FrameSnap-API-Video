package com.fiap.framesnap.infrastructure.video.controller.mapper;

import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.infrastructure.video.controller.dto.UploadVideoResponse;

public class VideoMapper {
    public static UploadVideoResponse toUploadResponse(Video video) {
        return new UploadVideoResponse(video.getId(), video.getFileName(), video.getStatus()); // ✅ Agora passa os 3 argumentos necessários
    }
}
