package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;

public class ValidateTokenUseCase {
    private final TokenGateway tokenGateway;

    public ValidateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public boolean isValid(String token) {
        return tokenGateway.validateToken(token);
    }
}
