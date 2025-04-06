package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUserVideosUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;

    public GetUserVideosUseCase(VideoRepositoryGateway videoRepositoryGateway) {
        this.videoRepositoryGateway = videoRepositoryGateway;
    }

    public List<Video> execute(String userEmail) {
        return videoRepositoryGateway.findByUserEmail(userEmail);
    }
} 