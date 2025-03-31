/*package com.fiap.framesnap.crosscutting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;


@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbClient dynamoDbClient() {
        String region = System.getenv("DYNAMODB_REGION");
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String sessionToken = System.getenv("AWS_SESSION_TOKEN");

        String region = "us-east-1";
        String accessKey = "ASIA2WJTGM7J7ER5GWAF";
        String secretKey = "SlJMYTEQtVpyS4mi1bv6zJinrPMhtgBCXFH6xhQZ";
        String sessionToken = "IQoJb3JpZ2luX2VjEKH//////////wEaCXVzLXdlc3QtMiJHMEUCIQDcv5HSRYjEXuNaAEJePhQLTqDEeeFlQ+EN7Ek94l/pRAIgD0ZqiqLdTNMha5GDeo9HfEHcYhNQ3/R2wCxEYLgDM1IqugII+v//////////ARAAGgw3MzUwODM2NTMwNzUiDBHXSp6CSJWEWsPhqCqOApKDbaT7S1dlvpqSebUMhHOT6fTZb5huHmm6lRchqpb2pJxY9cX5/4Z5GO/zRhOytQA/2x2gE8OgiLC896m3XwyEjOIIJIfDDXiNNQx7nW8QqEvxOWQiUCUXoBGnk6y/v42iiQKCme3j8ayUBh/JZ74Ak9IR4/jJZl0Kelz8yAAyrQgVLPtVgUddfa49QnMjtc9hV6rHtd50bQswWhrhJDjTui1Gfqb3B+vngPyGVoo0EXWdPZgEb46+qx5F5lByaJcBkK6AX3DaclyTpFTsPyKQMsGGfOF+1W9+P8QOCLEfzrxWnR2ZT3jPxSxcGGKMnBq7tKxuBrgJjgsP7jdab4QLDbqz5zk/K/QO+KKMyzD09oe/BjqdAUhMJL0wX2IvX6j2/S5TqZnLUOrdR7qRNe9+bxHfAdklGfwqrA0bpgBsrY/2J/pqAa1A4VuCYfpcSSaGldW3UiOZXDZ7r5/6gFD9cdz8WZt6+o2mFUJkAkCjF9k8IadGjgWcMdn1gSHv17di4JHOZ5LtxfitNSrNDF7BSzLmcplgRs9/dM0K5RIkSWFx5vgUqdok82tOG/c8mNAEtoM=";

        return DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsSessionCredentials.create(accessKey,secretKey,sessionToken)
                ))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}*/
