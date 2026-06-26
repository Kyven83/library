package com.amirsaleh.library.core.mapper;

import com.amirsaleh.library.core.dto.response.BookResponse;
import com.amirsaleh.library.domain.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setQuantity(book.getQuantity());
        response.setAvailable(book.isAvailable());
        return response;
    }

    public List<BookResponse> toResponseList(List<Book> books) {
        return books.stream()
                .map(this::toResponse)
                .toList();
    }
}