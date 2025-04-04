package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.entities.video.Video;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@Component
public class DownloadThumbnailsUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;
    private final VideoStorageGateway videoStorageGateway;

    public DownloadThumbnailsUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            VideoStorageGateway videoStorageGateway) {
        this.videoRepositoryGateway = videoRepositoryGateway;
        this.videoStorageGateway = videoStorageGateway;
    }

    public record ThumbnailDownloadResult(
            String fileName,
            String contentType,
            String base64Content
    ) {}

    public ThumbnailDownloadResult execute(String videoId) {
        UUID id = UUID.fromString(videoId);
        Video video = videoRepositoryGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));

        if (video.getThumbnailFileName() == null || video.getThumbnailFileName().isBlank()) {
            throw new RuntimeException("Thumbnails ainda não estão disponíveis");
        }

        try {
            InputStream inputStream = videoStorageGateway.downloadVideo(video.getThumbnailFileName());

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            byte[] fileContent = buffer.toByteArray();
            String base64Content = Base64.getEncoder().encodeToString(fileContent);

            return new ThumbnailDownloadResult(
                    video.getThumbnailFileName(),
                    "application/zip",
                    base64Content
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer download dos thumbnails", e);
        }
    }
}
