package com.fiap.framesnap.infrastructure.video.controller;

import com.fiap.framesnap.application.video.usecases.DownloadVideoUseCase;
import com.fiap.framesnap.application.video.usecases.UpdateVideoStatusUseCase;
import com.fiap.framesnap.application.video.usecases.UpdateVideoMetadataUseCase;
import com.fiap.framesnap.application.video.usecases.GetVideoStatusUseCase;
import com.fiap.framesnap.application.video.usecases.DownloadThumbnailsUseCase;
import com.fiap.framesnap.infrastructure.video.controller.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.fiap.framesnap.application.video.usecases.InitUploadUseCase;
import com.fiap.framesnap.entities.video.Video;

import java.util.Base64;

@RestController
@RequestMapping("/videos")
public class VideoApi {

    private final DownloadVideoUseCase downloadVideoUseCase;
    private final InitUploadUseCase initUploadUseCase;
    private final UpdateVideoStatusUseCase updateVideoStatusUseCase;
    private final UpdateVideoMetadataUseCase updateVideoMetadataUseCase;
    private final GetVideoStatusUseCase getVideoStatusUseCase;
    private final DownloadThumbnailsUseCase downloadThumbnailsUseCase;

    public VideoApi(
            DownloadVideoUseCase downloadVideoUseCase,
            InitUploadUseCase initUploadUseCase,
            UpdateVideoStatusUseCase updateVideoStatusUseCase,
            UpdateVideoMetadataUseCase updateVideoMetadataUseCase,
            GetVideoStatusUseCase getVideoStatusUseCase,
            DownloadThumbnailsUseCase downloadThumbnailsUseCase) {
        this.downloadVideoUseCase = downloadVideoUseCase;
        this.initUploadUseCase = initUploadUseCase;
        this.updateVideoStatusUseCase = updateVideoStatusUseCase;
        this.updateVideoMetadataUseCase = updateVideoMetadataUseCase;
        this.getVideoStatusUseCase = getVideoStatusUseCase;
        this.downloadThumbnailsUseCase = downloadThumbnailsUseCase;
    }

    @PostMapping("/init-upload")
    public ResponseEntity<InitUploadResponse> initUpload(@RequestBody InitUploadRequest request) {
        var output = initUploadUseCase.execute(request.fileName(), request.userEmail());
        return ResponseEntity.ok(new InitUploadResponse(output.videoId(), output.presignedUrl()));
    }

    @GetMapping("/download")
    public ResponseEntity<DownloadVideoResponse> downloadVideo(@RequestParam("videoId") String videoId) {
        String downloadUrl = downloadVideoUseCase.execute(videoId);
        return ResponseEntity.ok(new DownloadVideoResponse(downloadUrl));
    }

    @PutMapping("/{videoId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String videoId,
            @RequestBody UpdateVideoStatusRequest request) {
        updateVideoStatusUseCase.execute(videoId, request.status());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<Void> updateMetadata(
            @PathVariable String videoId,
            @RequestBody UpdateVideoMetadataRequest request) {
        updateVideoMetadataUseCase.execute(
            videoId,
            request.thumbnailFileName(),
            request.thumbnailUrl(),
            request.status()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{videoId}/status")
    public ResponseEntity<VideoStatusResponse> getStatus(@PathVariable String videoId) {
        Video video = getVideoStatusUseCase.execute(videoId);
        return ResponseEntity.ok(new VideoStatusResponse(
            video.getStatus().toString(),
            video.getThumbnailFileName(),
            video.getThumbnailUrl(),
            video.getProcessedAt()
        ));
    }

    @GetMapping("/{videoId}/download")
    public ResponseEntity<ThumbnailDownloadResponse> downloadThumbnails(@PathVariable String videoId) {
        var result = downloadThumbnailsUseCase.execute(videoId);
        return ResponseEntity.ok(new ThumbnailDownloadResponse(
            result.fileName(),
            result.contentType(),
            result.base64Content()
        ));
    }

    @GetMapping("/{videoId}/download/file")
    public ResponseEntity<byte[]> downloadThumbnailsAsFile(@PathVariable String videoId) {
        var result = downloadThumbnailsUseCase.execute(videoId);
        
        byte[] fileContent = Base64.getDecoder().decode(result.base64Content());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", result.fileName());
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(fileContent);
    }
}
