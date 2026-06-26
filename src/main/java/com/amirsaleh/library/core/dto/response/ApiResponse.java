package com.amirsaleh.library.core.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse<T> {

    private int statusCode;
    private List<String> messages;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .statusCode(200)
                .messages(List.of())  // ← آرایه خالی
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .statusCode(200)
                .messages(message != null ? List.of(message) : List.of())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .statusCode(200)
                .messages(message != null ? List.of(message) : List.of())
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .statusCode(200)
                .messages(List.of())
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return ApiResponse.<T>builder()
                .statusCode(statusCode)
                .messages(message != null ? List.of(message) : List.of())
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> error(int statusCode, List<String> messages) {
        return ApiResponse.<T>builder()
                .statusCode(statusCode)
                .messages(messages != null ? messages : List.of())
                .data(null)
                .build();
    }
}