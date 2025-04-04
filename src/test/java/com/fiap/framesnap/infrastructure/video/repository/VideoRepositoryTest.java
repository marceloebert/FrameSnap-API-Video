// Arquivo: VideoRepositoryTest.java
package com.fiap.framesnap.infrastructure.video.repository;

import com.fiap.framesnap.entities.video.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class VideoRepositoryTest {

    private DynamoDbEnhancedClient mockClient;
    private DynamoDbTable<Video> mockTable;
    private VideoRepository repository;

    @BeforeEach
    void setup() {
        mockClient = mock(DynamoDbEnhancedClient.class);
        mockTable = mock(DynamoDbTable.class);
        when(mockClient.table(any(), any())).thenReturn((DynamoDbTable) mockTable);
        repository = new VideoRepository(mockClient, "videoTable");
    }

    @Test
    void testSave() {
        Video video = mock(Video.class);
        repository.save(video);
        verify(mockTable).putItem(video);
    }

    @Test
    void testFindById_found() {
        UUID id = UUID.randomUUID();
        Video video = new Video();

        when(mockTable.getItem(any(Consumer.class))).thenReturn(video);

        Optional<Video> result = repository.findById(id);
        assertTrue(result.isPresent());
        assertEquals(video, result.get());
    }

    @Test
    void testFindById_notFound() {
        UUID id = UUID.randomUUID();
        when(mockTable.getItem(any(Consumer.class))).thenReturn(null);

        Optional<Video> result = repository.findById(id);
        assertTrue(result.isEmpty());
    }
}
