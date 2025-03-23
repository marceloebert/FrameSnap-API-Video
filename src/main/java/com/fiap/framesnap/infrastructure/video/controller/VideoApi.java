package com.fiap.framesnap.infrastructure.video.controller;

import com.fiap.framesnap.application.video.usecases.UploadVideoUseCase;
import com.fiap.framesnap.application.video.usecases.DownloadVideoUseCase;
import com.fiap.framesnap.infrastructure.video.controller.dto.*;
import com.fiap.framesnap.infrastructure.video.controller.mapper.VideoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.fiap.framesnap.infrastructure.video.controller.dto.UploadVideoResponse;
import com.fiap.framesnap.entities.video.Video;
import java.util.UUID;

@RestController
@RequestMapping("/videos")
public class VideoApi {

    private final UploadVideoUseCase uploadVideoUseCase;
    private final DownloadVideoUseCase downloadVideoUseCase;

    public VideoApi(UploadVideoUseCase uploadVideoUseCase, DownloadVideoUseCase downloadVideoUseCase) {
        this.uploadVideoUseCase = uploadVideoUseCase;
        this.downloadVideoUseCase = downloadVideoUseCase;
    }

    @PostMapping("/upload")
    public ResponseEntity<UUID> uploadVideo(@RequestParam("file") MultipartFile file,
                                            @RequestParam("userEmail") String userEmail) throws IOException {
        UUID videoId = uploadVideoUseCase.execute(file.getOriginalFilename(), file.getInputStream(), userEmail);
        return ResponseEntity.ok(videoId);
    }

    @GetMapping("/download")
    public ResponseEntity<DownloadVideoResponse> downloadVideo(@RequestParam("videoId") String videoId) {
        String downloadUrl = downloadVideoUseCase.execute(videoId);
        return ResponseEntity.ok(new DownloadVideoResponse(downloadUrl));
    }
}
