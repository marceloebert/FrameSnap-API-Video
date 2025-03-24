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
        String accessKey = "ASIA2WJTGM7JSSSAWILS";
        String secretKey = "k6EW1EsRCcCLXlrd0zBva5aRtiIkaujw2x3ggASn";
        String sessionToken = "IQoJb3JpZ2luX2VjEJr//////////wEaCXVzLXdlc3QtMiJHMEUCIQDIA9csAVXNZV/gL+Rq/IpvngIgG3zUZGwQfDeq/wKzDgIgEFtOB9TlPRW2s4Ay7kcoyEKsa+CnP/UxD18lTHeZuFUqugII8///////////ARAAGgw3MzUwODM2NTMwNzUiDJb/j83xzKnH+l8cFCqOAvZKmRCCONqkHQ3azEl0vzDdPZOu2jG4pNl1PXJheZjBgAprshpC+O2zcz6m+PVDgIXsw5juiJhhaSySFA9lwLrgQmIHKxlaZ2NWHmCctxVUQ+EryZZ6Nu8jKaIeDr4sq1VEn0tAqiPJVjtkhuijtz/0n70XsA+pbuAZTyIktVLjPh2Kp0meYPYuL3JhDajJsG7LV82bHuJJSyIkDn9UdWWDmNdM8CteUWGQ/8ZRlPnfSzGDZkpdhB9UT2qExpXTxwxFFtiCwfAkEbs74LAMcNov4sIMO3sv2X9tWM4XmAriuVC7k9mKrElnYF8U+wfBn190mF6rF9UUpONCT2nzqMnF9wgxJ4HL2EnRpJF5ETCPqoa/BjqdAf1w7CXr2K8xsnZRyRoMk2YgwtSde92PjHAFq2vd2mA5dvlw5iuk38hofkG4GQmI1uOcDTIjZeTaA7ka89XUSnH2pRJ3gP7l2e2LdVuPSGehdEhRlsZYLdox5B7TaUzgVz5GcHMxG7narFSrbuA57oG4w2lmL4wD1kXyV1lRzL9rlgHBJdb2DGD9XxmcVWt/ft8H/u26a9XliIAPnRk=";

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
