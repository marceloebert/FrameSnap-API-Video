package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.application.video.gateways.VideoQueueGateway;
import com.fiap.framesnap.infrastructure.video.gateway.SQSVideoQueueGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@EnableConfigurationProperties(SQSProperties.class)
public class SQSConfig {

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.create();
    }

    @Bean
    public VideoQueueGateway videoQueueGateway(SqsClient sqsClient, SQSProperties properties) {
        return new SQSVideoQueueGateway(sqsClient, properties.getQueueUrl());
    }
}
