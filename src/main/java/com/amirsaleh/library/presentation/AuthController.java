package com.amirsaleh.library.presentation;

import com.amirsaleh.library.application.AuthService;
import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.core.dto.response.ApiResponse;
import com.amirsaleh.library.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "register user")
    @Tag(name = "auth")
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody UserRegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(user, "کاربر با موفقیت ثبت شد"));
    }
}
