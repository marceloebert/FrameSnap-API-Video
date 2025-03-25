package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateVideoStatusUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;
    private final VideoStatusGateway videoStatusGateway;

    public UpdateVideoStatusUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            VideoStatusGateway videoStatusGateway) {
        this.videoRepositoryGateway = videoRepositoryGateway;
        this.videoStatusGateway = videoStatusGateway;
    }

    public void execute(String videoId, String status) {
        UUID id = UUID.fromString(videoId);
        Video video = videoRepositoryGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));

        VideoStatus newStatus = VideoStatus.fromString(status);
        video.setStatus(newStatus);

        videoRepositoryGateway.save(video);
        videoStatusGateway.updateStatus(id, status);
    }
} 