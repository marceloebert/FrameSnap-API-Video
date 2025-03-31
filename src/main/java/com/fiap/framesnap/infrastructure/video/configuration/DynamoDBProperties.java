package com.fiap.framesnap.infrastructure.video.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "aws.dynamodb")
@Getter
@Setter
public class DynamoDBProperties {
    private String endpoint;
    private String tableName;
} 