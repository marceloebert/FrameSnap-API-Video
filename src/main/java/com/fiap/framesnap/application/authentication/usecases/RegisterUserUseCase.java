package com.fiap.framesnap.application.authentication.usecases;

import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.entities.authentication.User;
import java.util.UUID;

public class RegisterUserUseCase {

    private final UserGateway userGateway;

    public RegisterUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User register(String email, String passwordHash) {
        User newUser = new User(UUID.randomUUID(), email, passwordHash);
        return userGateway.register(newUser);
    }
}
