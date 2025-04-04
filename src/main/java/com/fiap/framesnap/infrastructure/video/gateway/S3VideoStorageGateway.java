package com.fiap.framesnap.infrastructure.video.gateway;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.InputStream;
import java.time.Duration;

public class S3VideoStorageGateway implements VideoStorageGateway {

    private final S3Client s3Client;
    private final String bucketName;
    private final Region region;
    private final S3Presigner s3Presigner;

    public S3VideoStorageGateway(S3Client s3Client, S3Presigner s3Presigner, String bucketName, Region region) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucketName = bucketName;
        this.region = region;
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

    @Override
    public String generatePresignedUploadUrl(String objectKey) {
        try {
            System.out.println("Gerando URL assinada para:");
            System.out.println("Bucket: " + bucketName);
            System.out.println("Object Key: " + objectKey);
            System.out.println("Region: " + region);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType("video/mp4")
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(30))
                    .putObjectRequest(putObjectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
            String url = presignedRequest.url().toString();
            System.out.println("URL gerada com sucesso: " + url);
            return url;
        } catch (Exception e) {
            System.err.println("Erro ao gerar URL assinada: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar URL assinada do S3", e);
        }
    }
}
