package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.usecases.DownloadThumbnailsUseCase;
import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.infrastructure.video.gateway.S3VideoStorageGateway;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import com.fiap.framesnap.infrastructure.video.configuration.AwsCredentialsProperties;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
@EnableConfigurationProperties({AwsCredentialsProperties.class, S3Properties.class})
public class S3Config {

    private final S3Properties props;

    public S3Config(S3Properties props) {
        this.props = props;
    }

    @PostConstruct
    public void checkProps() {
        System.out.println("🧪 Endpoint configurado: " + props.getEndpoint());
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(props.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(props.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public VideoStorageGateway videoStorageGateway(S3Client s3Client, S3Presigner s3Presigner) {
        return new S3VideoStorageGateway(s3Client, s3Presigner, props.getBucket(), Region.of(props.getRegion()));
    }

    @Bean
    public DownloadThumbnailsUseCase downloadThumbnailsUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            VideoStorageGateway videoStorageGateway) {
        return new DownloadThumbnailsUseCase(
            videoRepositoryGateway,
            videoStorageGateway
        );
    }
}