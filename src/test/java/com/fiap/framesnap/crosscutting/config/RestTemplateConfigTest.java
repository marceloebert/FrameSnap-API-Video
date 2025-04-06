package com.fiap.framesnap.crosscutting.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateConfigTest {

    @Test
    void restTemplate_ShouldCreateInstance() {
        // Arrange
        RestTemplateConfig config = new RestTemplateConfig();

        // Act
        RestTemplate restTemplate = config.restTemplate();

        // Assert
        assertNotNull(restTemplate);
    }
} 