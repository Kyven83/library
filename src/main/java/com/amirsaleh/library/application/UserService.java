package com.amirsaleh.library.application;

import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.domain.User;
import com.amirsaleh.library.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(UUID id) {
        return  userRepository.findById(id).orElseThrow(() -> new IllegalStateException("کاربر یافت نشد"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(UUID id, UserRegisterRequest userRegisterRequest) {
        User user = userRepository.getUserById((id));
        if (userRegisterRequest.getFullName() != null && !userRegisterRequest.getFullName().trim().isEmpty()) {
            user.setFullName(userRegisterRequest.getFullName());
        }
        return userRepository.save(user);
    }
}
