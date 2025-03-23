package com.fiap.framesnap.infrastructure.authentication.gateway;

import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.configuration.CognitoProperties;
import com.fiap.framesnap.crosscutting.util.CognitoSecretHashGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import java.util.Map;
import java.util.HashMap;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class CognitoUserGateway implements UserGateway {

    private final CognitoIdentityProviderClient cognitoClient;
    private final CognitoProperties cognitoProperties;

    public CognitoUserGateway(CognitoIdentityProviderClient cognitoClient, CognitoProperties cognitoProperties) {
        this.cognitoClient = cognitoClient;
        this.cognitoProperties = cognitoProperties;
    }

    @Override
    public User register(User user) {
        // Verifica se o Client Secret está presente e gera o secretHash
        String clientSecret = cognitoProperties.getClientSecret();
        String secretHash = (clientSecret != null && !clientSecret.isEmpty())
                ? CognitoSecretHashGenerator.calculateSecretHash(
                cognitoProperties.getClientId(),
                clientSecret,
                user.getEmail()
        )
                : null;

        // Construção do SignUpRequest
        SignUpRequest.Builder signUpBuilder = SignUpRequest.builder()
                .clientId(cognitoProperties.getClientId())
                .username(user.getEmail())
                .password(user.getPassword())
                .userAttributes(List.of(
                        AttributeType.builder().name("email").value(user.getEmail()).build()
                ));

        if (secretHash != null) {
            signUpBuilder.secretHash(secretHash);
        }

        // Executa o registro no Cognito
        SignUpResponse response = cognitoClient.signUp(signUpBuilder.build());

        // Confirmando o usuário automaticamente
        cognitoClient.adminConfirmSignUp(AdminConfirmSignUpRequest.builder()
                .userPoolId(cognitoProperties.getUserPoolId()) // ID do User Pool
                .username(user.getEmail()) // E-mail do usuário
                .build());

        // Retorna um novo usuário com o ID gerado pelo Cognito
        return new User(UUID.fromString(response.userSub()), user.getEmail(), user.getPassword());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty(); // Implementação futura para busca de usuário no Cognito
    }

    @Override
    public String login(String email, String password) {
        // ✅ Gerando o SECRET_HASH corretamente
        String secretHash = CognitoSecretHashGenerator.calculateSecretHash(
                cognitoProperties.getClientId(),
                cognitoProperties.getClientSecret(),
                email
        );

        // ✅ Criando os parâmetros de autenticação
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", password);
        authParams.put("SECRET_HASH", secretHash);

        // ✅ Criando a requisição para autenticação
        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .clientId(cognitoProperties.getClientId())
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(authParams)
                .build();

        // ✅ Enviando a requisição para o Cognito
        InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);
        return authResponse.authenticationResult().idToken();
    }
}
