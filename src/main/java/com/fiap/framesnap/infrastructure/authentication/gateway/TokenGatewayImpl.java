package com.fiap.framesnap.infrastructure.authentication.gateway;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.crosscutting.util.HashUtil;


import java.util.HashMap;
import java.util.Map;

@Component
public class TokenGatewayImpl implements TokenGateway {

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.client-secret}")
    private String clientSecret;

    public TokenGatewayImpl(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @Override
    public String generateToken(String email, String password) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", password);
        authParams.put("SECRET_HASH", calculateSecretHash(email));

        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .clientId(clientId)
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(authParams)
                .build();

        InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);
        return authResponse.authenticationResult().idToken(); // Retorna o ID token
    }

    private String calculateSecretHash(String username) {
        // Aqui colocamos a mesma lógica de hash que usamos no registro
        return HashUtil.calculateSecretHash(clientId, clientSecret, username);
    }

    @Override
    public boolean validateToken(String token) {
        // Adapte esta lógica conforme necessário para validar o token
        return token != null && !token.trim().isEmpty();
    }
}
