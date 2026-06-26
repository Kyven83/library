package com.amirsaleh.library.application;

import com.amirsaleh.library.domain.Book;
import com.amirsaleh.library.infrastructure.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.amirsaleh.library.core.dto.request.bookRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public Book addBook(bookRequest book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalStateException("کتاب با این ISBN قبلا ثبت شده است");
        }

        Book newBook = new Book();
        newBook.setIsbn(book.getIsbn());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setQuantity(book.getQuantity());
        return bookRepository.save(newBook);
    }

    public Book getById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalStateException("کتاب پیدا نشد"));
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional
    public Book updateBook(UUID id, bookRequest updatedBook) {
        Book book = getById(id);
        book.setTitle(updatedBook.getTitle());
        book.setIsbn(updatedBook.getIsbn());
        book.setAuthor(updatedBook.getAuthor());
        book.setQuantity(updatedBook.getQuantity());
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalStateException("کتاب پیدا نشد");
        }
        bookRepository.deleteById(id);
    }
}
