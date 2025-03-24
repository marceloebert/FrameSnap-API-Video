package com.fiap.framesnap.infrastructure.video.controller;

import com.fiap.framesnap.application.video.usecases.DownloadVideoUseCase;
import com.fiap.framesnap.infrastructure.video.controller.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fiap.framesnap.application.video.usecases.InitUploadUseCase;

@RestController
@RequestMapping("/videos")
public class VideoApi {

    private final DownloadVideoUseCase downloadVideoUseCase;
    private final InitUploadUseCase initUploadUseCase;

    public VideoApi(DownloadVideoUseCase downloadVideoUseCase,
                   InitUploadUseCase initUploadUseCase) {
        this.downloadVideoUseCase = downloadVideoUseCase;
        this.initUploadUseCase = initUploadUseCase;
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
}
