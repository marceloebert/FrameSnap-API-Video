package com.fiap.framesnap.infrastructure.video.controller.dto;

public class UploadVideoRequest {
    private String fileName;
    private String userEmail;

    public UploadVideoRequest() {}

    public UploadVideoRequest(String fileName, String userEmail) {
        this.fileName = fileName;
        this.userEmail = userEmail;
    }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
