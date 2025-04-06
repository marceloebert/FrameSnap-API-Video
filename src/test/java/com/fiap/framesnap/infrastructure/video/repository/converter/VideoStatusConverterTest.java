package com.fiap.framesnap.infrastructure.video.repository.converter;

import com.fiap.framesnap.entities.video.VideoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import static org.junit.jupiter.api.Assertions.*;

class VideoStatusConverterTest {

    private VideoStatusConverter converter;

    @BeforeEach
    void setUp() {
        converter = new VideoStatusConverter();
    }

    @Test
    void transformFrom_ShouldConvertVideoStatusToAttributeValue() {
        // Arrange
        VideoStatus status = VideoStatus.COMPLETED;

        // Act
        AttributeValue result = converter.transformFrom(status);

        // Assert
        assertNotNull(result);
        assertEquals("COMPLETED", result.s());
    }

    @Test
    void transformTo_ShouldConvertAttributeValueToVideoStatus() {
        // Arrange
        AttributeValue attributeValue = AttributeValue.builder().s("COMPLETED").build();

        // Act
        VideoStatus result = converter.transformTo(attributeValue);

        // Assert
        assertEquals(VideoStatus.COMPLETED, result);
    }

    @Test
    void type_ShouldReturnVideoStatusType() {
        // Act
        EnhancedType<VideoStatus> type = converter.type();

        // Assert
        assertEquals(VideoStatus.class, type.rawClass());
    }

    @Test
    void attributeValueType_ShouldReturnStringType() {
        // Act
        AttributeValueType type = converter.attributeValueType();

        // Assert
        assertEquals(AttributeValueType.S, type);
    }

    @Test
    void transformTo_WithInvalidStatus_ShouldThrowException() {
        // Arrange
        AttributeValue attributeValue = AttributeValue.builder().s("INVALID_STATUS").build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> converter.transformTo(attributeValue));
    }
} 