package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.usecases.DownloadThumbnailsUseCase;
import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class S3ConfigTest {

    private S3Config s3Config;
    private S3Properties s3Properties;

    @BeforeEach
    void setUp() {
        s3Properties = new S3Properties();
        s3Properties.setEndpoint("http://localhost:4566");
        s3Properties.setBucket("test-bucket");
        s3Properties.setRegion("us-east-1");

        s3Config = new S3Config(s3Properties);
    }

    @Test
    void s3Client_ShouldCreateClientWithCorrectConfiguration() {
        // Act
        S3Client client = s3Config.s3Client();

        // Assert
        assertNotNull(client);
    }

    @Test
    void s3Presigner_ShouldCreatePresignerWithCorrectConfiguration() {
        // Act
        S3Presigner presigner = s3Config.s3Presigner();

        // Assert
        assertNotNull(presigner);
    }

    @Test
    void videoStorageGateway_ShouldCreateGatewayWithCorrectConfiguration() {
        // Arrange
        S3Client s3Client = mock(S3Client.class);
        S3Presigner s3Presigner = mock(S3Presigner.class);

        // Act
        VideoStorageGateway gateway = s3Config.videoStorageGateway(s3Client, s3Presigner);

        // Assert
        assertNotNull(gateway);
    }

    @Test
    void downloadThumbnailsUseCase_ShouldCreateUseCaseWithCorrectConfiguration() {
        // Arrange
        VideoRepositoryGateway videoRepositoryGateway = mock(VideoRepositoryGateway.class);
        VideoStorageGateway videoStorageGateway = mock(VideoStorageGateway.class);

        // Act
        DownloadThumbnailsUseCase useCase = s3Config.downloadThumbnailsUseCase(
            videoRepositoryGateway,
            videoStorageGateway
        );

        // Assert
        assertNotNull(useCase);
    }
} 