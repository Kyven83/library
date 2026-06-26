package com.amirsaleh.library.application;

import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.domain.User;
import com.amirsaleh.library.domain.enums.userRole;
import com.amirsaleh.library.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public User register(UserRegisterRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException("رمز عبور با تکرار آن مطابقت ندارد");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalStateException("کاربر با این شماره موبایل وجود دارد");
        }
        if(userRepository.existsByNationalCode(request.getNationalCode())){
            throw new IllegalStateException("کاربر با این کد ملی وجود دارد");
        }
        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setNationalCode(request.getNationalCode());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setPassword(request.getPassword());
        newUser.setTotalPenalty(0);
        newUser.setRole(userRole.getDefault());
        return userRepository.save(newUser);
    }

}
