package com.amirsaleh.library.presentation;

import com.amirsaleh.library.application.AuthService;
import com.amirsaleh.library.core.dto.request.UserLoginRequest;
import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.core.dto.response.ApiResponse;
import com.amirsaleh.library.core.dto.response.AuthResponse;
import com.amirsaleh.library.core.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "register user")
    @Tag(name = "auth")
    public ResponseEntity<AuthResponse> addUser(@RequestBody UserRegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "login user")
    @Tag(name = "auth")
    public ResponseEntity<AuthResponse> login(
            @RequestBody UserLoginRequest request,
            HttpServletResponse response) {

        AuthResponse authResponse = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("atkn", authResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(authResponse);
    }
}