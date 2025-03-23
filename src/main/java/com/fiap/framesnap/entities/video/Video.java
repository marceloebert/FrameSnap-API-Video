package com.fiap.framesnap.entities.video;

import java.time.Instant;
import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Video {
    private UUID id;
    private String fileName;
    private String userEmail;
    private String status;
    private Instant createdAt;

    public Video(UUID id, String fileName, String userEmail, String status, Instant createdAt) {
        this.id = id;
        this.fileName = fileName;
        this.userEmail = userEmail;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
