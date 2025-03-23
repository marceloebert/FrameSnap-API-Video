package com.fiap.framesnap.infrastructure.authentication.repository;

import com.fiap.framesnap.application.authentication.gateways.UserGateway;
import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.entity.UserEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserGateway {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User register(User user) {
        UserEntity userEntity = new UserEntity(null, user.getEmail(), user.getPassword());
        UserEntity savedUser = jpaUserRepository.save(userEntity);
        return new User(savedUser.getId(), savedUser.getEmail(), savedUser.getPassword());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(entity -> new User(entity.getId(), entity.getEmail(), entity.getPassword()));
    }

    @Override
    public String login(String email, String password) {
        return "";
    }

}
