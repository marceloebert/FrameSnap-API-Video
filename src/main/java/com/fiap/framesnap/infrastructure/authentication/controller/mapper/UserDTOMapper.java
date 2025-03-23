package com.fiap.framesnap.infrastructure.authentication.controller.mapper;

import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public User toUser(UserRequest userRequest) {
        return new User(null, userRequest.getEmail(), userRequest.getPassword());
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail());
    }
}
