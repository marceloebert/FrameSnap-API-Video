package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.gateways.UserGateway;

public class LoginUseCase {
    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;

    public LoginUseCase(UserGateway userGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
    }

    public String execute(String email, String password) {
        return tokenGateway.generateToken(email, password);
    }
}
