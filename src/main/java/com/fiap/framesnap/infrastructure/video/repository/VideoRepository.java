package com.fiap.framesnap.infrastructure.video.repository;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Optional;
import java.util.UUID;

@Repository
public class VideoRepository implements VideoRepositoryGateway {

    private final DynamoDbTable<Video> videoTable;

    public VideoRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.videoTable = dynamoDbEnhancedClient.table("videos", TableSchema.fromBean(Video.class));
    }

    @Override
    public void save(Video video) {
        videoTable.putItem(video);
    }

    @Override
    public Optional<Video> findById(UUID videoId) {
        Video video = videoTable.getItem(r -> r.key(k -> k.partitionValue(videoId.toString())));
        return Optional.ofNullable(video);
    }
}
