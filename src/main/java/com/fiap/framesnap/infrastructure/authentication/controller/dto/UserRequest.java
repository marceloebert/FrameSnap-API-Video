package com.fiap.framesnap.infrastructure.authentication.controller.dto;


public class UserRequest {
    private String email;
    private String password;

    public UserRequest() {}

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}