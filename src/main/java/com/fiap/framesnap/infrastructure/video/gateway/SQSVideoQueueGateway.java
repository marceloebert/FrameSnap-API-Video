package com.fiap.framesnap.infrastructure.video.gateway;

import com.fiap.framesnap.application.video.gateways.VideoQueueGateway;
import com.fiap.framesnap.entities.video.Video;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SQSVideoQueueGateway implements VideoQueueGateway {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public SQSVideoQueueGateway(SqsClient sqsClient, String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    @Override
    public void sendMessage(Video video) {
        String messageBody = String.format(
                "{\"videoId\": \"%s\", \"fileName\": \"%s\", \"status\": \"%s\"}",
                video.getId(), video.getFileName(), video.getStatus()
        );

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        sqsClient.sendMessage(request);
    }
}
