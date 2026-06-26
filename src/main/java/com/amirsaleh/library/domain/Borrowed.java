package com.amirsaleh.library.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Borrowed {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnedDate;

    private Integer delayDays;
    private Integer totalPenalty;
    private Boolean isReturned;

    @PrePersist
    public void prePersist() {
        this.borrowedDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(14);
        this.isReturned = false;
        this.delayDays = 0;
        this.totalPenalty = 0;
    }
}