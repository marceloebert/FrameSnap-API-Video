package com.fiap.framesnap.infrastructure.helthcheck.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApi {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
