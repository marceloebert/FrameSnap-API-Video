package com.fiap.framesnap.infrastructure.authentication.configuration;

import com.fiap.framesnap.application.authentication.gateways.TokenGateway;
import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.application.authentication.usecases.LoginUseCase;
import com.fiap.framesnap.application.authentication.usecases.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    public LoginUseCase loginUseCase(UserGateway userGateway, TokenGateway tokenGateway) {
        return new LoginUseCase(userGateway, tokenGateway); // Removendo PasswordEncoder que não deveria estar aqui
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserGateway userGateway) {
        return new RegisterUserUseCase(userGateway); // Removendo PasswordEncoder que não deveria estar aqui
    }
}
