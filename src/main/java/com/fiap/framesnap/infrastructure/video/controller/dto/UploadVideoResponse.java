package com.fiap.framesnap.infrastructure.video.controller.dto;

import java.util.UUID;

public class UploadVideoResponse {
    private UUID id;
    private String fileName;
    private String status;

    public UploadVideoResponse(UUID id, String fileName, String status) {
        this.id = id;
        this.fileName = fileName;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getStatus() {
        return status;
    }
}
