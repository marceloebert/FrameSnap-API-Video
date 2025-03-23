package com.fiap.framesnap.application.authentication.gateways;

public interface TokenGateway {
    String generateToken(String email, String password);
    boolean validateToken(String token);
}
