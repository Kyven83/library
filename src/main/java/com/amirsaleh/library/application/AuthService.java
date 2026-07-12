package com.amirsaleh.library.application;

import com.amirsaleh.library.core.config.JwtService;
import com.amirsaleh.library.core.dto.request.UserLoginRequest;
import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.core.dto.response.AuthResponse;
import com.amirsaleh.library.core.dto.response.UserResponse;
import com.amirsaleh.library.domain.User;
import com.amirsaleh.library.domain.enums.userRole;
import com.amirsaleh.library.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(UserRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException("رمز عبور با تکرار آن مطابقت ندارد");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalStateException("کاربر با این شماره موبایل وجود دارد");
        }
        if (userRepository.existsByNationalCode(request.getNationalCode())) {
            throw new IllegalStateException("کاربر با این کد ملی وجود دارد");
        }

        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setNationalCode(request.getNationalCode());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setTotalPenalty(0);
        newUser.setRole(userRole.getDefault());

        User savedUser = userRepository.save(newUser);
        UserResponse userResponse = convertToUserResponse(savedUser);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        return new AuthResponse(userResponse, accessToken, refreshToken);
    }

    public AuthResponse login(UserLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNationalCode(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("کد ملی یا رمز عبور اشتباه است");
        }

        User user = userRepository.findByNationalCode(request.getNationalCode())
                .orElseThrow(() -> new BadCredentialsException("کد ملی یا رمز عبور اشتباه است"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        UserResponse userResponse = convertToUserResponse(user);
        return new AuthResponse(userResponse, accessToken, refreshToken);
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setNationalCode(user.getNationalCode());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setTotalPenalty(user.getTotalPenalty());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}