package com.amirsaleh.library.application;

import com.amirsaleh.library.core.mapper.UserMapper;
import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.core.dto.response.UserResponse;
import com.amirsaleh.library.domain.User;
import com.amirsaleh.library.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("کاربر پیدا نشد"));
    }

    public UserResponse getById(UUID id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(() -> new IllegalStateException("کاربر یافت نشد")));
    }

    public List<UserResponse> getAll() {
        return userMapper.toResponse(userRepository.findAll());
    }

    @Transactional
    public UserResponse updateUser(UUID id, UserRegisterRequest userRegisterRequest) {
        User user = userRepository.getUserById((id));
        if (userRegisterRequest.getFullName() != null && !userRegisterRequest.getFullName().trim().isEmpty()) {
            user.setFullName(userRegisterRequest.getFullName());
        }
        return userMapper.toResponse(userRepository.save(user));
    }
}
