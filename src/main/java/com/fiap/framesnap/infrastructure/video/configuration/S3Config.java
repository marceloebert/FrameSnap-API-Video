package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoStorageGateway;
import com.fiap.framesnap.application.video.usecases.DownloadThumbnailsUseCase;
import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.infrastructure.video.gateway.S3VideoStorageGateway;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import com.fiap.framesnap.infrastructure.video.configuration.AwsCredentialsProperties;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

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
    public S3Client s3Client(AwsCredentialsProperties creds) {
        var credentials = AwsSessionCredentials.create(
                creds.getAccessKey(),
                creds.getSecretKey(),
                creds.getSessionToken()
        );

        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Client.builder()
                .region(Region.of(creds.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(props.getEndpoint()))
                .serviceConfiguration(s3Configuration)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(AwsCredentialsProperties creds) {
        var credentials = AwsSessionCredentials.create(
                creds.getAccessKey(),
                creds.getSecretKey(),
                creds.getSessionToken()
        );

        return S3Presigner.builder()
                .region(Region.of(creds.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public VideoStorageGateway videoStorageGateway(S3Client s3Client, S3Presigner s3Presigner) {
        return new S3VideoStorageGateway(s3Client, s3Presigner, props.getBucket());
    }

    @Bean
    public DownloadThumbnailsUseCase downloadThumbnailsUseCase(
            VideoRepositoryGateway videoRepositoryGateway,
            S3Client s3Client) {
        return new DownloadThumbnailsUseCase(
            videoRepositoryGateway,
            s3Client,
            props.getBucket()
        );
    }
}