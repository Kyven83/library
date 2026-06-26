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
import java.util.ArrayList;
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
    public List<Borrowed> borrowBooks(UUID userId, List<UUID> bookIds) {

        User user = userService.getEntityById(userId);

        int borrowedCount = borrowedRepository.countByUserAndIsReturnedFalse(user);

        if (borrowedCount + bookIds.size() > MAX_BORROW_LIMIT) {
            throw new IllegalStateException(
                    String.format("کاربر حداکثر %d کتاب می‌تواند امانت بگیرد", MAX_BORROW_LIMIT));
        }

        List<Book> books = bookRepository.findAllById(bookIds);

        if (books.size() != bookIds.size()) {
            throw new IllegalStateException("یک یا چند کتاب پیدا نشد.");
        }

        List<UUID> borrowedBookIds =
                borrowedRepository.findBorrowedBookIds(userId);

        List<Borrowed> borrowedList = new ArrayList<>();

        for (Book book : books) {

            if (book.getQuantity() <= 0) {
                throw new IllegalStateException(
                        "کتاب " + book.getTitle() + " موجود نیست.");
            }

            if (borrowedBookIds.contains(book.getId())) {
                throw new IllegalStateException(
                        "کاربر قبلاً کتاب " + book.getTitle() + " را امانت گرفته است.");
            }

            book.setQuantity(book.getQuantity() - 1);

            Borrowed borrowed = new Borrowed();
            borrowed.setUser(user);
            borrowed.setBook(book);

            borrowedList.add(borrowed);
        }

        bookRepository.saveAll(books);

        return borrowedRepository.saveAll(borrowedList);
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
