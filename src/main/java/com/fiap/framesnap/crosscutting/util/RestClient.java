package com.fiap.framesnap.crosscutting.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(request, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    }

    public <T> ResponseEntity<T> put(String url, Object request, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(request, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    }

    public ResponseEntity<Void> delete(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }
}

