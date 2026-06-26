package com.amirsaleh.library.infrastructure;

import com.amirsaleh.library.domain.Borrowed;
import com.amirsaleh.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowedRepository extends JpaRepository<Borrowed, UUID> {

    List<Borrowed> findByUserIdAndIsReturnedFalse(UUID userId);

    List<Borrowed> findByUserId(UUID userId);

    boolean existsByUserIdAndBookIdAndIsReturnedFalse(UUID userId, UUID bookId);

    int countByUserAndIsReturnedFalse(User user);

    @Query("select b from Borrowed b where b.isReturned = false and b.dueDate < current_timestamp")
    List<Borrowed> findAllOverdue();

    @Query("""
       select b.book.id
       from Borrowed b
       where b.user.id = :userId
       and b.isReturned = false
       """)
    List<UUID> findBorrowedBookIds(UUID userId);
}
