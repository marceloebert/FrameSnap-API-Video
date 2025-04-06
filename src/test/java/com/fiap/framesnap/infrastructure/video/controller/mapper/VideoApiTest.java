// Arquivo: VideoApiTest.java
package com.fiap.framesnap.infrastructure.video.controller.mapper;

import com.fiap.framesnap.application.video.usecases.*;
import com.fiap.framesnap.entities.video.Video;
import com.fiap.framesnap.entities.video.VideoStatus;
import com.fiap.framesnap.infrastructure.video.controller.VideoApi;
import com.fiap.framesnap.infrastructure.video.controller.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoApiTest {

    @Mock private DownloadVideoUseCase downloadVideoUseCase;
    @Mock private InitUploadUseCase initUploadUseCase;
    @Mock private UpdateVideoStatusUseCase updateVideoStatusUseCase;
    @Mock private UpdateVideoMetadataUseCase updateVideoMetadataUseCase;
    @Mock private GetVideoStatusUseCase getVideoStatusUseCase;
    @Mock private DownloadThumbnailsUseCase downloadThumbnailsUseCase;
    @Mock private GetUserVideosUseCase getUserVideosUseCase;

    @InjectMocks private VideoApi videoApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        videoApi = new VideoApi(downloadVideoUseCase, initUploadUseCase, updateVideoStatusUseCase,
                updateVideoMetadataUseCase, getVideoStatusUseCase, downloadThumbnailsUseCase, getUserVideosUseCase);
    }

    @Test
    void testInitUpload() {
        UUID videoId = UUID.randomUUID();
        String url = "https://example.com/upload";
        InitUploadRequest request = new InitUploadRequest("file.mp4", "email@example.com");
        when(initUploadUseCase.execute(any(), any())).thenReturn(new InitUploadUseCase.InitUploadOutput(videoId.toString(), url));

        ResponseEntity<InitUploadResponse> response = videoApi.initUpload(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(videoId.toString(), response.getBody().videoId());
        assertEquals(url, response.getBody().presignedUrl());
    }

    @Test
    void testDownloadVideo() {
        UUID videoId = UUID.randomUUID();
        String url = "https://video.com/v.mp4";
        when(downloadVideoUseCase.execute(anyString())).thenReturn(url);

        ResponseEntity<DownloadVideoResponse> response = videoApi.downloadVideo(videoId.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(url, response.getBody().getDownloadUrl());
    }

    @Test
    void testUpdateStatus() {
        UUID videoId = UUID.randomUUID();
        UpdateVideoStatusRequest request = new UpdateVideoStatusRequest("COMPLETED");

        ResponseEntity<Void> response = videoApi.updateStatus(videoId.toString(), request);

        assertEquals(200, response.getStatusCodeValue());
        verify(updateVideoStatusUseCase).execute(videoId.toString(), "COMPLETED");
    }

    @Test
    void testUpdateMetadata() {
        UUID videoId = UUID.randomUUID();
        UpdateVideoMetadataRequest request = new UpdateVideoMetadataRequest("thumb.jpg", "http://img", "COMPLETED");

        ResponseEntity<Void> response = videoApi.updateMetadata(videoId.toString(), request);

        assertEquals(200, response.getStatusCodeValue());
        verify(updateVideoMetadataUseCase).execute(videoId.toString(), "thumb.jpg", "http://img", "COMPLETED");
    }

    @Test
    void testDownloadThumbnails() {
        UUID videoId = UUID.randomUUID();
        when(downloadThumbnailsUseCase.execute(any())).thenReturn(new DownloadThumbnailsUseCase.ThumbnailDownloadResult("thumb.jpg", MediaType.IMAGE_JPEG_VALUE, Base64.getEncoder().encodeToString("fake".getBytes())));

        ResponseEntity<ThumbnailDownloadResponse> response = videoApi.downloadThumbnails(videoId.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("thumb.jpg", response.getBody().fileName());
    }

    @Test
    void testDownloadThumbnailsAsFile() {
        UUID videoId = UUID.randomUUID();
        when(downloadThumbnailsUseCase.execute(any())).thenReturn(new DownloadThumbnailsUseCase.ThumbnailDownloadResult("thumb.jpg", MediaType.IMAGE_JPEG_VALUE, Base64.getEncoder().encodeToString("fake".getBytes())));

        ResponseEntity<byte[]> response = videoApi.downloadThumbnailsAsFile(videoId.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("thumb.jpg", response.getHeaders().getContentDisposition().getFilename());
    }
}
