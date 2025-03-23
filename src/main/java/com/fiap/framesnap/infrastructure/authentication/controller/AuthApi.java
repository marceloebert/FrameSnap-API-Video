package com.fiap.framesnap.infrastructure.authentication.controller;

import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserResponse;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.TokenResponse;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;
import com.fiap.framesnap.infrastructure.authentication.controller.mapper.LoginMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthApi(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        User user = registerUserUseCase.register(userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getEmail()));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = loginUseCase.execute(request.getEmail(), request.getPassword());
        return LoginMapper.toResponse(token);
    }


}
