package com.amirsaleh.library.core.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String fullName;
    private String nationalCode;
    private String phoneNumber;
    private Integer totalPenalty;
    private LocalDateTime createdAt;
}
