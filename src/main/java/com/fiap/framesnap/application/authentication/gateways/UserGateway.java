package com.fiap.framesnap.application.authentication.gateways;

import com.fiap.framesnap.entities.authentication.User;
import java.util.Optional;

public interface UserGateway {
    User register(User user); // Mudamos para receber um objeto User, seguindo o Clean Architecture
    Optional<User> findByEmail(String email);
    String login(String email, String password);
}
