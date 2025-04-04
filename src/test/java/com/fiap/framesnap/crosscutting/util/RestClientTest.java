package com.fiap.framesnap.crosscutting.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = new RestClient(restTemplate);
    }

    @Test
    void post_ShouldSendPostRequest() {
        // Arrange
        String url = "http://test.com/api";
        String requestBody = "request data";
        String responseBody = "response data";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.post(url, requestBody, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseBody, response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void get_ShouldSendGetRequest() {
        // Arrange
        String url = "http://test.com/api";
        String responseBody = "response data";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.get(url, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseBody, response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void put_ShouldSendPutRequest() {
        // Arrange
        String url = "http://test.com/api";
        String requestBody = "request data";
        String responseBody = "response data";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.PUT),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.put(url, requestBody, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseBody, response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.PUT),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void delete_ShouldSendDeleteRequest() {
        // Arrange
        String url = "http://test.com/api";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        ResponseEntity<Void> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.DELETE),
            any(HttpEntity.class),
            eq(Void.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Void> response = restClient.delete(url, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.DELETE),
            any(HttpEntity.class),
            eq(Void.class)
        );
    }
} 