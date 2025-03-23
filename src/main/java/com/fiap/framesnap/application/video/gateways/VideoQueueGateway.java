package com.fiap.framesnap.application.video.gateways;

import com.fiap.framesnap.entities.video.Video;

public interface VideoQueueGateway {
    void sendMessage(Video video);
}
