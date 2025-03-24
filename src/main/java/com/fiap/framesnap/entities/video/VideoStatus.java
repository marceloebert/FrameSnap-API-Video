package com.fiap.framesnap.entities.video;

public enum VideoStatus {
    PENDING_UPLOAD("PENDING_UPLOAD"),
    UPLOADING("UPLOADING"),
    UPLOADED("UPLOADED"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED"),
    ERROR("ERROR");

    private final String status;

    VideoStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

    public static VideoStatus fromString(String text) {
        for (VideoStatus status : VideoStatus.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Nenhum status encontrado para " + text);
    }
} 