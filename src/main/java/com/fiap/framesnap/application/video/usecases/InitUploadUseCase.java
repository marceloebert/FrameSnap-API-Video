package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.time.Instant;

@Component
public class InitUploadUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;
    private final VideoStorageGateway videoStorageGateway;
    private final VideoStatusGateway videoStatusGateway;

    public InitUploadUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            VideoStorageGateway videoStorageGateway,
            VideoStatusGateway videoStatusGateway) {
        this.videoRepositoryGateway = videoRepositoryGateway;
        this.videoStorageGateway = videoStorageGateway;
        this.videoStatusGateway = videoStatusGateway;
    }

    public InitUploadOutput execute(String fileName, String userEmail) {
        try {
            // Gera um ID único para o vídeo
            UUID videoId = UUID.randomUUID();
            String key = videoId + "_" + fileName;

            // Gera a URL pré-assinada para upload
            String presignedUrl = videoStorageGateway.generatePresignedUploadUrl(key);

            // Cria o vídeo com status inicial e a URL pré-assinada
            Video video = new Video(
                videoId,
                fileName,
                userEmail,
                VideoStatus.PENDING_UPLOAD,
                Instant.now(),
                presignedUrl,
                "", // thumbnailFileName vazio
                "", // thumbnailUrl vazio
                null // processedAt null
            );

            // Salva os metadados no DynamoDB
            videoRepositoryGateway.save(video);

            // Atualiza o status no Redis
            videoStatusGateway.updateStatus(videoId, video.getStatus().toString());

            return new InitUploadOutput(videoId.toString(), presignedUrl);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar upload do vídeo", e);
        }
    }

    public record InitUploadOutput(String videoId, String presignedUrl) {}
}
