package com.fiap.framesnap.infrastructure.helthcheck.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class HealthCheckApiTest {

    @Test
    void healthCheck_ShouldReturnOk() {
        // Arrange
        HealthCheckApi controller = new HealthCheckApi();

        // Act
        String response = controller.healthCheck();

        // Assert
        assertNotNull(response);
        assertEquals("OK", response);
    }
} 