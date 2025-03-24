package com.fiap.framesnap.entities.video;

import java.time.Instant;
import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import com.fiap.framesnap.infrastructure.video.repository.converter.VideoStatusConverter;

@DynamoDbBean
public class Video {
    private UUID id;
    private String fileName;
    private String userEmail;
    private VideoStatus status;
    private Instant createdAt;
    private String presignedUrl;
    private String thumbnailFileName;
    private String thumbnailUrl;
    private Instant processedAt;

    public Video(UUID id, String fileName, String userEmail, VideoStatus status, 
                Instant createdAt, String presignedUrl, String thumbnailFileName, 
                String thumbnailUrl, Instant processedAt) {
        this.id = id;
        this.fileName = fileName;
        this.userEmail = userEmail;
        this.status = status;
        this.createdAt = createdAt;
        this.presignedUrl = presignedUrl;
        this.thumbnailFileName = thumbnailFileName;
        this.thumbnailUrl = thumbnailUrl;
        this.processedAt = processedAt;
    }

    // Construtor para criação inicial do vídeo
    public Video(UUID id, String fileName, String userEmail, VideoStatus status, 
                Instant createdAt, String presignedUrl) {
        this(id, fileName, userEmail, status, createdAt, presignedUrl, null, null, null);
    }

    public Video() {
    }

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @DynamoDbConvertedBy(VideoStatusConverter.class)
    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPresignedUrl() {
        return presignedUrl;
    }

    public void setPresignedUrl(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }
}
