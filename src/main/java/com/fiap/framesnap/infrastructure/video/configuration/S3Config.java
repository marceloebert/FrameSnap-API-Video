package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.infrastructure.video.gateway.S3VideoStorageGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.create(); // usa default credentials + region
    }

    @Bean
    public VideoStorageGateway videoStorageGateway(S3Client s3Client) {
        return new S3VideoStorageGateway(s3Client);
    }
}
