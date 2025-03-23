package com.fiap.framesnap.infrastructure.video.controller.dto;

import java.util.UUID;

public class DownloadVideoRequest {
    private UUID videoId;

    public DownloadVideoRequest() {}

    public DownloadVideoRequest(UUID videoId) {
        this.videoId = videoId;
    }

    public UUID getVideoId() { return videoId; }
    public void setVideoId(UUID videoId) { this.videoId = videoId; }
}
