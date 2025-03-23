package com.fiap.framesnap.crosscutting.config;

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
        /*String region = System.getenv("DYNAMODB_REGION");
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String sessionToken = System.getenv("AWS_SESSION_TOKEN");*/

        String region = "us-east-1";
        String accessKey = "ASIA2WJTGM7JY722QGUA";
        String secretKey = "xbTk/q3xuOzWE1VLUNjLcLPjwfNiylpJzXrwV/6/";
        String sessionToken = "IQoJb3JpZ2luX2VjEFwaCXVzLXdlc3QtMiJGMEQCIHEi8FJyYh+XrC5y4mKocA2WFt5lQwVlzF/c0Iz7AJrHAiBHECuPaPWXT9oPgI3A0mOR9q4duXI4T//UhMysHkVMlCq6Agi1//////////8BEAAaDDczNTA4MzY1MzA3NSIMUXwiE/wlqUB9KQr5Ko4CUE9wYa67RfAH+YpQXo1xw0L8hxklIw7kuJcjDYMT+JbsivPwi1opCeJjVSJoRO5YK4l8d/SnHjUvvIwhD42Cj/xARxkdn/WswqSDX6okwefb9zwpJ4lQUCG6eMA/B/M5hwFpdZY35+eCFpp3iQkkWs5gOxoxJ1kfjkjPBgOea660gvEVF3hCqPK4CxE+TRIa0FYdOneyp+dboTQTrn1lU6W2uPve3WBOZH4eVROKa3jrbsfRmXlLumySA/rTdvPsZB8zOpO3snJ3aw8CXaWS65qmPOxSSr3sIGOjjcd7cVSiCDWx4yCgQF/bfX7SYF0HkiDvNl6KVuXbcASYXD9sYtXY/b82HopJeGxnL7k3MK7a+L4GOp4BFbBfl9bmOVv7LNrab+LsiL/TPcTmyey0RhLt5gPNTQFrTUs8UpCJCbv5O+UGoIMVNaxD0zfT00kUvQpHatHmAswprQIDaKq9moaqllhIpKLRoGpFmxjIuTXnFjHimSmHCnTfOeRhiMBoYEgg5tqlaOIcxO19O3G3TzlCY2NGsker9O7FgXy1MD0dqr1t/FfdMFVxmOiVGoD+BcU3urE=";

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
}
