package com.fiap.framesnap.application.video.gateways;

import java.io.InputStream;

public interface VideoStorageGateway {
    void uploadVideo(String fileName, InputStream videoStream);
    InputStream downloadVideo(String fileName); // <-- esse aqui é importante
    String getDownloadUrl(String videoKey);
}
