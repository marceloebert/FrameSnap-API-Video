package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoRepositoryGateway;
import com.fiap.framesnap.infrastructure.video.repository.VideoRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
@EnableConfigurationProperties({DynamoDBProperties.class, AwsCredentialsProperties.class})
@Slf4j
public class DynamoDBConfig {

    private final DynamoDBProperties props;
    private final AwsCredentialsProperties awsCredentials;

    public DynamoDBConfig(DynamoDBProperties props, AwsCredentialsProperties awsCredentials) {
        this.props = props;
        this.awsCredentials = awsCredentials;
    }

    @PostConstruct
    public void init() {
        log.info("DynamoDB endpoint configured: {}", props.getEndpoint());
        log.info("DynamoDB table name configured: {}", props.getTableName());
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(props.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsSessionCredentials.create(
                                awsCredentials.getAccessKey(),
                                awsCredentials.getSecretKey(),
                                awsCredentials.getSessionToken()
                        )
                ))
                .region(Region.of(awsCredentials.getRegion()))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public VideoRepository videoRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return new VideoRepository(dynamoDbEnhancedClient, props.getTableName());
    }
} 