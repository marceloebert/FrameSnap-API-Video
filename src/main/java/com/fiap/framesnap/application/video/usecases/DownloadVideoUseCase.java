package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import org.springframework.stereotype.Component;

@Component
public class DownloadVideoUseCase {
    private final VideoStorageGateway storageGateway;

    public DownloadVideoUseCase(VideoStorageGateway storageGateway) {
        this.storageGateway = storageGateway;
    }

    public String execute(String videoId) {
        return storageGateway.getDownloadUrl(videoId);
    }
}

