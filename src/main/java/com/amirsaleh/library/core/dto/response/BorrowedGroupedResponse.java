package com.amirsaleh.library.core.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class BorrowedGroupedResponse {
    private UUID userId;
    private String userFullName;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private List<BookBorrowInfo> books;
    private Integer totalDelayDays;
    private Integer totalPenalty;
    private Boolean isAllReturned;

    @Data
    public static class BookBorrowInfo {
        private UUID borrowId;
        private UUID bookId;
        private String bookTitle;
        private String isbn;
        private LocalDateTime returnedDate;
        private Boolean isReturned;
    }
}