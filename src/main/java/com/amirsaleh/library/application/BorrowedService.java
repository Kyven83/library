package com.amirsaleh.library.application;

import com.amirsaleh.library.domain.Book;
import com.amirsaleh.library.domain.Borrowed;
import com.amirsaleh.library.domain.User;
import com.amirsaleh.library.infrastructure.BookRepository;
import com.amirsaleh.library.infrastructure.BorrowedRepository;
import com.amirsaleh.library.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowedService {
    private final BorrowedRepository borrowedRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final UserService userService;
    private final BookService bookService;

    private static final int MAX_BORROW_LIMIT = 3;

    @Transactional
    public Borrowed borrowBook(UUID userId, UUID bookId) {
        User user = userService.getById(userId);
        Book book = bookService.getById(bookId);

        int borrowedNumber = borrowedRepository.countByUserAndIsReturnedFalse(user);

        if(borrowedNumber >= MAX_BORROW_LIMIT) {
            throw new IllegalStateException(String.format("کاربر میتواند حداکثر %s کتاب به امانت ببرد", MAX_BORROW_LIMIT));
        }
        if(book.getQuantity() <= 0){
            throw new IllegalStateException("کتاب موجود نمیباشد");
        }
        if (borrowedRepository.existsByUserIdAndBookIdAndIsReturnedFalse(userId, bookId)) {
            throw new IllegalStateException("کاربر هم اکنون این کتاب را به امانت برده");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        Borrowed borrowed = new Borrowed();
        borrowed.setUser(user);
        borrowed.setBook(book);

        return borrowedRepository.save(borrowed);
    }

    @Transactional
    public Borrowed returnBook(UUID borrowedId) {
        Borrowed borrowed = borrowedRepository.findById(borrowedId)
                .orElseThrow(() -> new IllegalStateException("رکورد امانت پیدا نشد"));

        if (Boolean.TRUE.equals(borrowed.getIsReturned())) {
            throw new IllegalStateException("این کتاب قبلا برگشت داده شده");
        }

        LocalDateTime now = LocalDateTime.now();

        borrowed.setReturnedDate(now);
        borrowed.setIsReturned(true);

        if(now.isAfter(borrowed.getDueDate())){
            int delayDay = (int) Duration.between(borrowed.getDueDate(), now).toDays();
            borrowed.setDelayDays(delayDay);
            borrowed.setTotalPenalty(delayDay * 500_000);
        }

        Book book = borrowed.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        return borrowedRepository.save(borrowed);
    }

    public List<Borrowed> getActiveBorrowed(UUID userId) {
        return borrowedRepository.findByUserIdAndIsReturnedFalse(userId);
    }
    public List<Borrowed> getBorrowedHistory(UUID userId) {
        return borrowedRepository.findByUserId(userId);
    }
    public List<Borrowed> getOverdue(){
        return borrowedRepository.findAllOverdue();
    }
}
