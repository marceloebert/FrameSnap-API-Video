package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;
import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.application.video.gateways.VideoQueueGateway;

@Component
public class UploadVideoUseCase {

    private final VideoRepositoryGateway videoRepositoryGateway;
    private final VideoStorageGateway videoStorageGateway;
    private final VideoStatusGateway videoStatusGateway;
    private final VideoQueueGateway videoQueueGateway;

    public UploadVideoUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            VideoStorageGateway videoStorageGateway,
            VideoStatusGateway videoStatusGateway,
            VideoQueueGateway videoQueueGateway
    ) {
        this.videoRepositoryGateway = videoRepositoryGateway;
        this.videoStorageGateway = videoStorageGateway;
        this.videoStatusGateway = videoStatusGateway;
        this.videoQueueGateway = videoQueueGateway;
    }

    public UUID execute(String fileName, InputStream fileStream, String userEmail) {
        UUID videoId = UUID.randomUUID();
        VideoStatus initialStatus = VideoStatus.UPLOADING;

        String s3Key = videoId.toString() + "_" + fileName;

        // 1. Salvar o status no Redis
        videoStatusGateway.updateStatus(videoId, initialStatus.toString());

        // 2. Upload do vídeo no S3
        videoStorageGateway.uploadVideo(s3Key, fileStream);

        // 3. Salvar os metadados no Dynamo
        Video video = new Video(videoId, s3Key, userEmail, initialStatus, Instant.now(), null);
        videoRepositoryGateway.save(video);

        // 4. Enviar mensagem para SQS
        videoQueueGateway.sendMessage(video);

        return videoId;
    }
}
