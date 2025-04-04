package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Component
public class DownloadThumbnailsUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;
    private final S3Client s3Client;
    private final String bucketName;

    public DownloadThumbnailsUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            S3Client s3Client,
            String bucketName) {
        this.videoRepositoryGateway = videoRepositoryGateway;
        this.s3Client = s3Client;
        this.bucketName = bucketName;
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
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(video.getThumbnailFileName())
                    .build();

            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = response.read(data, 0, data.length)) != -1) {
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
