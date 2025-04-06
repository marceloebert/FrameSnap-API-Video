package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserVideosUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    private GetUserVideosUseCase getUserVideosUseCase;

    @BeforeEach
    void setUp() {
        getUserVideosUseCase = new GetUserVideosUseCase(videoRepositoryGateway);
    }

    @Test
    void execute_ShouldReturnUserVideos() {
        // Arrange
        String userEmail = "test@example.com";
        UUID videoId1 = UUID.randomUUID();
        UUID videoId2 = UUID.randomUUID();
        Instant now = Instant.now();

        Video video1 = new Video(
            videoId1,
            "video1.mp4",
            userEmail,
            VideoStatus.COMPLETED,
            now,
            "presigned-url-1",
            "thumbnail1.jpg",
            "thumbnail-url-1",
            now
        );

        Video video2 = new Video(
            videoId2,
            "video2.mp4",
            userEmail,
            VideoStatus.PROCESSING,
            now,
            "presigned-url-2"
        );

        List<Video> expectedVideos = Arrays.asList(video1, video2);
        when(videoRepositoryGateway.findByUserEmail(userEmail)).thenReturn(expectedVideos);

        // Act
        List<Video> result = getUserVideosUseCase.execute(userEmail);

        // Assert
        assertEquals(expectedVideos, result);
    }
} 