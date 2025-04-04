package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.gateways.VideoStatusGateway;
import com.fiap.framesnap.application.video.gateways.VideoQueueGateway;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadVideoUseCaseTest {

    @Mock
    private VideoRepositoryGateway videoRepositoryGateway;

    @Mock
    private VideoStorageGateway videoStorageGateway;

    @Mock
    private VideoStatusGateway videoStatusGateway;

    @Mock
    private VideoQueueGateway videoQueueGateway;

    private UploadVideoUseCase uploadVideoUseCase;

    @BeforeEach
    void setUp() {
        uploadVideoUseCase = new UploadVideoUseCase(
            videoRepositoryGateway,
            videoStorageGateway,
            videoStatusGateway,
            videoQueueGateway
        );
    }

    @Test
    void execute_WhenUploadSuccessful_ShouldReturnVideoId() {
        // Arrange
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        byte[] fileContent = "test content".getBytes();
        InputStream fileStream = new ByteArrayInputStream(fileContent);

        doNothing().when(videoStorageGateway).uploadVideo(anyString(), any(InputStream.class));
        doNothing().when(videoRepositoryGateway).save(any(Video.class));
        doNothing().when(videoStatusGateway).updateStatus(any(UUID.class), anyString());
        doNothing().when(videoQueueGateway).sendMessage(any(Video.class));

        // Act
        UUID result = uploadVideoUseCase.execute(fileName, fileStream, userEmail);

        // Assert
        assertNotNull(result);
        
        verify(videoStatusGateway).updateStatus(
            eq(result),
            eq(VideoStatus.UPLOADING.toString())
        );
        
        verify(videoStorageGateway).uploadVideo(
            argThat(key -> key.endsWith(fileName) && key.startsWith(result.toString())),
            eq(fileStream)
        );
        
        verify(videoRepositoryGateway).save(argThat(video -> 
            video.getId().equals(result) &&
            video.getFileName().equals(result.toString() + "_" + fileName) &&
            video.getUserEmail().equals(userEmail) &&
            video.getStatus() == VideoStatus.UPLOADING
        ));
        
        verify(videoQueueGateway).sendMessage(argThat(video -> 
            video.getId().equals(result)
        ));
    }

    @Test
    void execute_WhenStorageUploadFails_ShouldThrowException() {
        // Arrange
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        byte[] fileContent = "test content".getBytes();
        InputStream fileStream = new ByteArrayInputStream(fileContent);

        doThrow(new RuntimeException("Storage error"))
            .when(videoStorageGateway).uploadVideo(anyString(), any(InputStream.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            uploadVideoUseCase.execute(fileName, fileStream, userEmail);
        });

        verify(videoStatusGateway).updateStatus(any(), eq(VideoStatus.UPLOADING.toString()));
        verify(videoRepositoryGateway, never()).save(any());
        verify(videoQueueGateway, never()).sendMessage(any());
    }

    @Test
    void execute_WhenRepositorySaveFails_ShouldThrowException() {
        // Arrange
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        byte[] fileContent = "test content".getBytes();
        InputStream fileStream = new ByteArrayInputStream(fileContent);

        doNothing().when(videoStorageGateway).uploadVideo(anyString(), any(InputStream.class));
        doThrow(new RuntimeException("Repository error"))
            .when(videoRepositoryGateway).save(any());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            uploadVideoUseCase.execute(fileName, fileStream, userEmail);
        });

        verify(videoStatusGateway).updateStatus(any(), eq(VideoStatus.UPLOADING.toString()));
        verify(videoStorageGateway).uploadVideo(anyString(), any());
        verify(videoQueueGateway, never()).sendMessage(any());
    }

    @Test
    void execute_WhenQueueSendFails_ShouldThrowException() {
        // Arrange
        String fileName = "test.mp4";
        String userEmail = "test@example.com";
        byte[] fileContent = "test content".getBytes();
        InputStream fileStream = new ByteArrayInputStream(fileContent);

        doNothing().when(videoStorageGateway).uploadVideo(anyString(), any(InputStream.class));
        doNothing().when(videoRepositoryGateway).save(any());
        doThrow(new RuntimeException("Queue error"))
            .when(videoQueueGateway).sendMessage(any());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            uploadVideoUseCase.execute(fileName, fileStream, userEmail);
        });

        verify(videoStatusGateway).updateStatus(any(), eq(VideoStatus.UPLOADING.toString()));
        verify(videoStorageGateway).uploadVideo(anyString(), any());
        verify(videoRepositoryGateway).save(any());
    }
} 