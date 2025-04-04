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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    private RestClient restClient;
    private final String TEST_URL = "http://test.com/api";

    @BeforeEach
    void setUp() {
        restClient = new RestClient(restTemplate);
    }

    @Test
    void post_ShouldSendPostRequestWithHeaders() {
        // Arrange
        TestRequest request = new TestRequest("test");
        TestResponse expectedResponse = new TestResponse("success");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        headers.put("Custom-Header", "value");

        ResponseEntity<TestResponse> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        when(restTemplate.exchange(
            eq(TEST_URL),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(TestResponse.class)
        )).thenReturn(responseEntity);

        // Act
        ResponseEntity<TestResponse> response = restClient.post(TEST_URL, request, TestResponse.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(restTemplate).exchange(
            eq(TEST_URL),
            eq(HttpMethod.POST),
            argThat(entity -> {
                HttpHeaders httpHeaders = entity.getHeaders();
                return httpHeaders.getContentType() == MediaType.APPLICATION_JSON &&
                       "Bearer token".equals(httpHeaders.getFirst("Authorization")) &&
                       "value".equals(httpHeaders.getFirst("Custom-Header")) &&
                       request.equals(entity.getBody());
            }),
            eq(TestResponse.class)
        );
    }

    @Test
    void get_ShouldSendGetRequestWithHeaders() {
        // Arrange
        TestResponse expectedResponse = new TestResponse("success");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");

        ResponseEntity<TestResponse> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        when(restTemplate.exchange(
            eq(TEST_URL),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(TestResponse.class)
        )).thenReturn(responseEntity);

        // Act
        ResponseEntity<TestResponse> response = restClient.get(TEST_URL, TestResponse.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(restTemplate).exchange(
            eq(TEST_URL),
            eq(HttpMethod.GET),
            argThat(entity -> {
                HttpHeaders httpHeaders = entity.getHeaders();
                return "Bearer token".equals(httpHeaders.getFirst("Authorization"));
            }),
            eq(TestResponse.class)
        );
    }

    @Test
    void put_ShouldSendPutRequestWithHeaders() {
        // Arrange
        TestRequest request = new TestRequest("test");
        TestResponse expectedResponse = new TestResponse("success");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");

        ResponseEntity<TestResponse> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        when(restTemplate.exchange(
            eq(TEST_URL),
            eq(HttpMethod.PUT),
            any(HttpEntity.class),
            eq(TestResponse.class)
        )).thenReturn(responseEntity);

        // Act
        ResponseEntity<TestResponse> response = restClient.put(TEST_URL, request, TestResponse.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(restTemplate).exchange(
            eq(TEST_URL),
            eq(HttpMethod.PUT),
            argThat(entity -> {
                HttpHeaders httpHeaders = entity.getHeaders();
                return httpHeaders.getContentType() == MediaType.APPLICATION_JSON &&
                       "Bearer token".equals(httpHeaders.getFirst("Authorization")) &&
                       request.equals(entity.getBody());
            }),
            eq(TestResponse.class)
        );
    }

    @Test
    void delete_ShouldSendDeleteRequestWithHeaders() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(restTemplate.exchange(
            eq(TEST_URL),
            eq(HttpMethod.DELETE),
            any(HttpEntity.class),
            eq(Void.class)
        )).thenReturn(responseEntity);

        // Act
        ResponseEntity<Void> response = restClient.delete(TEST_URL, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(restTemplate).exchange(
            eq(TEST_URL),
            eq(HttpMethod.DELETE),
            argThat(entity -> {
                HttpHeaders httpHeaders = entity.getHeaders();
                return "Bearer token".equals(httpHeaders.getFirst("Authorization"));
            }),
            eq(Void.class)
        );
    }

    private record TestRequest(String data) {}
    private record TestResponse(String result) {}
} 