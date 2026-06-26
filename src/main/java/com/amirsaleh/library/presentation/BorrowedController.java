package com.amirsaleh.library.presentation;

import com.amirsaleh.library.application.BorrowedService;
import com.amirsaleh.library.core.dto.response.ApiResponse;
import com.amirsaleh.library.domain.Borrowed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.amirsaleh.library.core.dto.request.borrowedRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/borrowed")
@RequiredArgsConstructor
@Tag(name="borrowed")
public class BorrowedController {

    private final BorrowedService borrowedService;

    @GetMapping("/borrowed-history/{userId}")
    @Operation(summary = "get borrowed book")
    public ResponseEntity<ApiResponse<List<Borrowed>>> getBorrowedUsers(@PathVariable UUID userId) {
        List<Borrowed> borrowed = borrowedService.getBorrowedHistory(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(borrowed));
    }

    @GetMapping("/active-borrowed/{userId}")
    @Operation(summary = "get active borrowed book")
    public ResponseEntity<ApiResponse<List<Borrowed>>> getActiveBorrowedUsers(@PathVariable UUID userId) {
        List<Borrowed> borrowed = borrowedService.getActiveBorrowed(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(borrowed));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Borrowed>>> borrowedBook(@RequestBody borrowedRequest request) {
        List<Borrowed> borrowed = borrowedService.borrowBooks(request.getUserId(), request.getBookId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(borrowed));
    }
}
