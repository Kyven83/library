package com.amirsaleh.library.core.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BorrowedResponse {

    private UUID id;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;

    private LocalDateTime returnedDate;

    private Integer delayDays;
    private Integer totalPenalty;
    private Boolean isReturned;

    private BookInfo book;
    private UserInfo user;

    @Data
    public static class BookInfo {
        private UUID id;
        private String title;
        private String isbn;
    }

    @Data
    public static class UserInfo {
        private UUID id;
        private String fullName;
    }
}