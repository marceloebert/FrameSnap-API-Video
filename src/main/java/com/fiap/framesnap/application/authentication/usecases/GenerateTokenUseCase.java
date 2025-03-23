package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;

public class GenerateTokenUseCase {
    private final TokenGateway tokenGateway;

    public GenerateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public String execute(String email, String password) {
        return tokenGateway.generateToken(email, password);
    }
}
