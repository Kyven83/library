package com.amirsaleh.library.presentation;

import com.amirsaleh.library.application.BorrowedService;
import com.amirsaleh.library.core.dto.request.borrowedRequest;
import com.amirsaleh.library.core.dto.response.ApiResponse;
import com.amirsaleh.library.core.dto.response.BorrowedGroupedResponse;
import com.amirsaleh.library.core.dto.response.BorrowedResponse;
import com.amirsaleh.library.core.mapper.BorrowedMapper;
import com.amirsaleh.library.domain.Borrowed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/borrowed")
@RequiredArgsConstructor
@Tag(name = "borrowed")
@SecurityRequirement(name = "bearerAuth")
public class BorrowedController {

    private final BorrowedService borrowedService;
    private final BorrowedMapper borrowedMapper;

    @GetMapping("/borrowed-history/{userId}")
    @Operation(summary = "get borrowed history")
    public ResponseEntity<ApiResponse<List<BorrowedGroupedResponse>>> getBorrowedHistory(@PathVariable UUID userId) {
        List<Borrowed> borrowed = borrowedService.getBorrowedHistory(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(borrowedMapper.toGroupedResponseList(borrowed), ""));
    }

    @GetMapping("/active-borrowed/{userId}")
    @Operation(summary = "get active borrowed books")
    public ResponseEntity<ApiResponse<List<BorrowedGroupedResponse>>> getActiveBorrowed(@PathVariable UUID userId) {
        List<Borrowed> borrowed = borrowedService.getActiveBorrowed(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(borrowedMapper.toGroupedResponseList(borrowed), ""));
    }

    @PostMapping
    @Operation(summary = "borrow books")
    public ResponseEntity<ApiResponse<List<BorrowedGroupedResponse>>> borrowBooks(@RequestBody borrowedRequest request) {
        List<Borrowed> borrowed = borrowedService.borrowBooks(request.getUserId(), request.getBookId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(borrowedMapper.toGroupedResponseList(borrowed), "کتاب با موفقیت امانت گرفته شد"));
    }

    @PatchMapping("/return/{borrowedId}")
    @Operation(summary = "return book")
    public ResponseEntity<ApiResponse<BorrowedResponse>> returnBook(@PathVariable UUID borrowedId) {
        Borrowed borrowed = borrowedService.returnBook(borrowedId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(borrowedMapper.toResponse(borrowed), "کتاب با موفقیت برگشت داده شد"));
    }
}