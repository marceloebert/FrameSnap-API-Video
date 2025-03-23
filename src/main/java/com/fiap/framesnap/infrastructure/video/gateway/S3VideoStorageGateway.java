package com.fiap.framesnap.infrastructure.video.gateway;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.InputStream;

public class S3VideoStorageGateway implements VideoStorageGateway {

    private final S3Client s3Client;
    private final String bucketName = "framesnap-video-bucket";

    public S3VideoStorageGateway(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void uploadVideo(String fileName, InputStream videoStream) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromInputStream(videoStream, videoStream.available())
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload para S3", e);
        }
    }

    @Override
    public InputStream downloadVideo(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
    }

    @Override
    public String getDownloadUrl(String videoKey) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, videoKey);
    }
}
