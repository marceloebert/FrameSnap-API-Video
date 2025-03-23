package com.fiap.framesnap.infrastructure.authentication.controller.mapper;

import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;

public class LoginMapper {
    public static LoginResponse toResponse(String token) {
        return new LoginResponse(token);
    }
}
