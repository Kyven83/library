package com.amirsaleh.library.presentation;

import com.amirsaleh.library.application.UserService;
import com.amirsaleh.library.core.dto.request.UserRegisterRequest;
import com.amirsaleh.library.core.dto.response.ApiResponse;
import com.amirsaleh.library.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "get all users")
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get user by id")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable UUID id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update profile")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable UUID id, @RequestBody UserRegisterRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "اطلاعات کاربر با موفقیت آپدیت شد"));
    }
}
