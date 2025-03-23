package com.fiap.framesnap.application.video.gateways;

import java.util.Optional;
import java.util.UUID;

public interface VideoStatusGateway {
    void updateStatus(UUID videoId, String status);
    Optional<String> findStatusById(UUID videoId);
}
