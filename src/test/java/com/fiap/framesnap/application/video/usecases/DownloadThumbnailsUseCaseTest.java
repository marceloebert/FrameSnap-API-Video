package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DownloadThumbnailsUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private S3Client s3Client;

    private DownloadThumbnailsUseCase downloadThumbnailsUseCase;
    private String bucketName = "test-bucket";

    @BeforeEach
    void setUp() {
        downloadThumbnailsUseCase = new DownloadThumbnailsUseCase(
                videoRepositoryGateway,
                s3Client,
                bucketName
        );
    }

    @Test
    void execute_WhenVideoExistsAndThumbnailsAvailable_ShouldReturnThumbnailData() throws IOException {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String thumbnailFileName = "thumbnails.zip";
        String thumbnailUrl = "https://example.com/thumbnails.zip";
        
        Video video = new Video(
                videoId,
                "test-video.mp4",
                "test@example.com",
                VideoStatus.COMPLETED,
                Instant.now(),
                "presigned-url",
                thumbnailFileName,
                thumbnailUrl,
                Instant.now()
        );
        
        byte[] testData = "test data".getBytes();
        ResponseInputStream<GetObjectResponse> responseInputStream = 
                new ResponseInputStream<>(
                        GetObjectResponse.builder().build(),
                        new ByteArrayInputStream(testData)
                );
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(responseInputStream);
        
        // Act
        DownloadThumbnailsUseCase.ThumbnailDownloadResult result = downloadThumbnailsUseCase.execute(videoId.toString());
        
        // Assert
        assertNotNull(result);
        assertEquals(thumbnailFileName, result.fileName());
        assertEquals("application/zip", result.contentType());
        assertNotNull(result.base64Content());
        assertTrue(result.base64Content().length() > 0);
        
        GetObjectRequest expectedRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(thumbnailFileName)
                .build();
        verify(s3Client).getObject(expectedRequest);
    }
    
    @Test
    void execute_WhenVideoNotFound_ShouldThrowException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> downloadThumbnailsUseCase.execute(videoId.toString()));
        verify(s3Client, never()).getObject(any(GetObjectRequest.class));
    }
    
    @Test
    void execute_WhenThumbnailFileNameIsNull_ShouldThrowException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        Video video = new Video(
                videoId,
                "test-video.mp4",
                "test@example.com",
                VideoStatus.PROCESSING,
                Instant.now(),
                "presigned-url"
        );
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> downloadThumbnailsUseCase.execute(videoId.toString()));
        verify(s3Client, never()).getObject(any(GetObjectRequest.class));
    }
    
    @Test
    void execute_WhenThumbnailFileNameIsEmpty_ShouldThrowException() {
        // Arrange
        UUID videoId = UUID.randomUUID();
        Video video = new Video(
                videoId,
                "test-video.mp4",
                "test@example.com",
                VideoStatus.PROCESSING,
                Instant.now(),
                "presigned-url",
                "",
                "",
                null
        );
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> downloadThumbnailsUseCase.execute(videoId.toString()));
        assertEquals("Thumbnails ainda não estão disponíveis", exception.getMessage());
        verify(s3Client, never()).getObject(any(GetObjectRequest.class));
    }
    
    @Test
    void execute_WhenS3ClientThrowsException_ShouldThrowRuntimeException() throws IOException {
        // Arrange
        UUID videoId = UUID.randomUUID();
        String thumbnailFileName = "thumbnails.zip";
        
        Video video = new Video(
                videoId,
                "test-video.mp4",
                "test@example.com",
                VideoStatus.COMPLETED,
                Instant.now(),
                "presigned-url",
                thumbnailFileName,
                "https://example.com/thumbnails.zip",
                Instant.now()
        );
        
        when(videoRepositoryGateway.findById(videoId)).thenReturn(Optional.of(video));
        
        // Configurar o mock para lançar exceção
        GetObjectRequest expectedRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(thumbnailFileName)
                .build();
        
        when(s3Client.getObject(expectedRequest)).thenThrow(new IOException("Test error"));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> downloadThumbnailsUseCase.execute(videoId.toString()));
        assertTrue(exception.getMessage().contains("Erro ao baixar thumbnails"));
    }
} 