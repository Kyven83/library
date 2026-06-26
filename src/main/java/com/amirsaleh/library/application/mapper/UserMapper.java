package com.amirsaleh.library.application.mapper;

import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.core.dto.response.UserResponse;
import com.amirsaleh.library.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setNationalCode(user.getNationalCode());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setTotalPenalty(user.getTotalPenalty());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    public List<UserResponse> toResponse(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    public User toEntity(UserRegisterRequest request) {

        if (request == null) {
            return null;
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setNationalCode(request.getNationalCode());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());

        return user;
    }
}