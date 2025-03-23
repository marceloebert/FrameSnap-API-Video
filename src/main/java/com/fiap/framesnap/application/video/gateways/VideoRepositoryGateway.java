package com.fiap.framesnap.application.video.gateways;

import com.fiap.framesnap.entities.video.Video;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepositoryGateway {
    void save(Video video);
    Optional<Video> findById(UUID videoId);
}
