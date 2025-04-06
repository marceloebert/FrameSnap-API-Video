package com.fiap.framesnap.infrastructure.video.configuration;

import com.fiap.framesnap.infrastructure.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DynamoDBConfigTest {

    private DynamoDBConfig dynamoDBConfig;
    private DynamoDBProperties dynamoDBProperties;
    private AwsCredentialsProperties awsCredentials;

    @BeforeEach
    void setUp() {
        dynamoDBProperties = new DynamoDBProperties();
        dynamoDBProperties.setEndpoint("http://localhost:4566");
        dynamoDBProperties.setTableName("test-table");

        awsCredentials = new AwsCredentialsProperties();
        awsCredentials.setAccessKey("test-access-key");
        awsCredentials.setSecretKey("test-secret-key");
        awsCredentials.setSessionToken("test-session-token");
        awsCredentials.setRegion("us-east-1");

        dynamoDBConfig = new DynamoDBConfig(dynamoDBProperties, awsCredentials);
    }

    @Test
    void dynamoDbClient_ShouldCreateClientWithCorrectConfiguration() {
        // Act
        DynamoDbClient client = dynamoDBConfig.dynamoDbClient();

        // Assert
        assertNotNull(client);
    }

    @Test
    void dynamoDbEnhancedClient_ShouldCreateClientWithCorrectConfiguration() {
        // Arrange
        DynamoDbClient dynamoDbClient = mock(DynamoDbClient.class);

        // Act
        DynamoDbEnhancedClient client = dynamoDBConfig.dynamoDbEnhancedClient(dynamoDbClient);

        // Assert
        assertNotNull(client);
    }

    @Test
    void videoRepository_ShouldCreateRepositoryWithCorrectConfiguration() {
        // Arrange
        DynamoDbEnhancedClient dynamoDbEnhancedClient = mock(DynamoDbEnhancedClient.class);

        // Act
        VideoRepository repository = dynamoDBConfig.videoRepository(dynamoDbEnhancedClient);

        // Assert
        assertNotNull(repository);
    }
} 