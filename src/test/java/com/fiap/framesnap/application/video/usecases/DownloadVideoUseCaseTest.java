package com.fiap.framesnap.application.video.usecases;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DownloadVideoUseCaseTest {

    @Mock
    private VideoStorageGateway videoStorageGateway;

    private DownloadVideoUseCase downloadVideoUseCase;

    @BeforeEach
    void setUp() {
        downloadVideoUseCase = new DownloadVideoUseCase(videoStorageGateway);
    }

    @Test
    void execute_ShouldReturnDownloadUrl() {
        // Arrange
        String fileName = "test.mp4";
        String expectedUrl = "http://example.com/test.mp4";
        
        when(videoStorageGateway.getDownloadUrl(fileName)).thenReturn(expectedUrl);

        // Act
        String actualUrl = downloadVideoUseCase.execute(fileName);

        // Assert
        assertEquals(expectedUrl, actualUrl);
    }
} 