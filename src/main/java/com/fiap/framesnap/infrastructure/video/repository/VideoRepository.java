package com.fiap.framesnap.infrastructure.video.repository;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class VideoRepository implements VideoRepositoryGateway {

    private final DynamoDbTable<Video> videoTable;

    public VideoRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, String tableName) {
        this.videoTable = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(Video.class));
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

    @Override
    public List<Video> findByUserEmail(String userEmail) {
        return videoTable
                .index("userEmail-index")
                .query(QueryEnhancedRequest.builder()
                        .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(userEmail).build()))
                        .build())
                .stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }

}
