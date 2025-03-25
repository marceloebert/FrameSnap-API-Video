package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetVideoStatusUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;
    private final VideoStatusGateway videoStatusGateway;

    public GetVideoStatusUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            VideoStatusGateway videoStatusGateway) {
        this.videoRepositoryGateway = videoRepositoryGateway;
        this.videoStatusGateway = videoStatusGateway;
    }

    public Video execute(String videoId) {
        UUID id = UUID.fromString(videoId);
        
        // Primeiro tenta buscar o status no Redis
        var redisStatus = videoStatusGateway.findStatusById(id);
        
        // Se encontrou no Redis, busca os detalhes completos no DynamoDB
        if (redisStatus.isPresent()) {
            return videoRepositoryGateway.findById(id)
                    .map(video -> {
                        // Atualiza o status do vídeo com o valor mais recente do Redis
                        video.setStatus(VideoStatus.fromString(redisStatus.get()));
                        return video;
                    })
                    .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));
        }
        
        // Se não encontrou no Redis, busca direto no DynamoDB
        return videoRepositoryGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));
    }
} 