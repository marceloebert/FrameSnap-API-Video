package com.fiap.framesnap.infrastructure.video.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DynamoDBPropertiesTest {

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        DynamoDBProperties properties = new DynamoDBProperties();
        String endpoint = "http://localhost:4566";
        String tableName = "test-table";

        // Act
        properties.setEndpoint(endpoint);
        properties.setTableName(tableName);

        // Assert
        assertEquals(endpoint, properties.getEndpoint());
        assertEquals(tableName, properties.getTableName());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyInstance() {
        // Arrange & Act
        DynamoDBProperties properties = new DynamoDBProperties();

        // Assert
        assertNull(properties.getEndpoint());
        assertNull(properties.getTableName());
    }
} 