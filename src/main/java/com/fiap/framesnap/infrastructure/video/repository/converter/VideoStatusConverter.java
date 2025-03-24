package com.fiap.framesnap.infrastructure.video.repository.converter;

import com.fiap.framesnap.entities.video.VideoStatus;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class VideoStatusConverter implements AttributeConverter<VideoStatus> {

    @Override
    public AttributeValue transformFrom(VideoStatus status) {
        return AttributeValue.builder().s(status.toString()).build();
    }

    @Override
    public VideoStatus transformTo(AttributeValue attributeValue) {
        return VideoStatus.fromString(attributeValue.s());
    }

    @Override
    public EnhancedType<VideoStatus> type() {
        return EnhancedType.of(VideoStatus.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
} 