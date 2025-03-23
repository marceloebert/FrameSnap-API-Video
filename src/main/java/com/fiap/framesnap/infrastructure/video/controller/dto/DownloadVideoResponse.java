package com.fiap.framesnap.infrastructure.video.controller.dto;

public class DownloadVideoResponse {
    private String downloadUrl;

    public DownloadVideoResponse() {}

    public DownloadVideoResponse(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
}
