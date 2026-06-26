package com.amirsaleh.library.presentation;

import com.amirsaleh.library.application.BookService;
import com.amirsaleh.library.core.dto.request.bookRequest;
import com.amirsaleh.library.core.dto.response.ApiResponse;
import com.amirsaleh.library.core.dto.response.BookResponse;
import com.amirsaleh.library.core.mapper.BookMapper;
import com.amirsaleh.library.domain.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Tag(name = "book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    @Operation(summary = "get all books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAll() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(bookMapper.toResponseList(books), ""));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get book by id")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable UUID id) {
        Book book = bookService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(bookMapper.toResponse(book), ""));
    }

    @PostMapping
    @Operation(summary = "add new book")
    public ResponseEntity<ApiResponse<BookResponse>> addBook(@RequestBody bookRequest request) {
        Book newBook = bookService.addBook(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookMapper.toResponse(newBook), "کتاب با موفقیت اضافه شد"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update book by id")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable UUID id, @RequestBody bookRequest request) {
        Book updatedBook = bookService.updateBook(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(bookMapper.toResponse(updatedBook), "کتاب با موفقیت ویرایش شد"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete book by id")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("کتاب با موفقیت حذف شد"));
    }
}