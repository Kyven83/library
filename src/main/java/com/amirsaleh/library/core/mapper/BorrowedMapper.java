package com.amirsaleh.library.core.mapper;

import com.amirsaleh.library.core.dto.response.BorrowedGroupedResponse;
import com.amirsaleh.library.core.dto.response.BorrowedResponse;
import com.amirsaleh.library.domain.Borrowed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowedMapper {

    public BorrowedResponse toResponse(Borrowed b) {
        BorrowedResponse response = new BorrowedResponse();
        response.setId(b.getId());
        response.setBorrowedDate(b.getBorrowedDate());
        response.setDueDate(b.getDueDate());
        response.setReturnedDate(b.getReturnedDate());
        response.setDelayDays(b.getDelayDays());
        response.setTotalPenalty(b.getTotalPenalty());
        response.setIsReturned(b.getIsReturned());

        if (b.getBook() != null) {
            BorrowedResponse.BookInfo book = new BorrowedResponse.BookInfo();
            book.setId(b.getBook().getId());
            book.setTitle(b.getBook().getTitle());
            book.setIsbn(b.getBook().getIsbn());
            response.setBook(book);
        }

        if (b.getUser() != null) {
            BorrowedResponse.UserInfo user = new BorrowedResponse.UserInfo();
            user.setId(b.getUser().getId());
            user.setFullName(b.getUser().getFullName());
            response.setUser(user);
        }

        return response;
    }

    public List<BorrowedGroupedResponse> toGroupedResponseList(List<Borrowed> list) {
        if (list == null || list.isEmpty()) return List.of();

        return list.stream()
                .collect(Collectors.groupingBy(b ->
                        b.getUser().getId() + "_" + b.getBorrowedDate().toLocalDate()
                ))
                .values().stream()
                .map(this::createGroupedResponse)
                .collect(Collectors.toList());
    }

    private BorrowedGroupedResponse createGroupedResponse(List<Borrowed> list) {
        Borrowed first = list.get(0);
        BorrowedGroupedResponse res = new BorrowedGroupedResponse();

        res.setUserId(first.getUser().getId());
        res.setUserFullName(first.getUser().getFullName());
        res.setBorrowedDate(first.getBorrowedDate());
        res.setDueDate(first.getDueDate());
        res.setBooks(list.stream().map(this::toBookInfo).collect(Collectors.toList()));
        res.setTotalDelayDays(list.stream().mapToInt(b -> b.getDelayDays() != null ? b.getDelayDays() : 0).sum());
        res.setTotalPenalty(list.stream().mapToInt(b -> b.getTotalPenalty() != null ? b.getTotalPenalty() : 0).sum());
        res.setIsAllReturned(list.stream().allMatch(Borrowed::getIsReturned));

        return res;
    }

    private BorrowedGroupedResponse.BookBorrowInfo toBookInfo(Borrowed b) {
        BorrowedGroupedResponse.BookBorrowInfo info = new BorrowedGroupedResponse.BookBorrowInfo();
        info.setBorrowId(b.getId());
        info.setBookId(b.getBook().getId());
        info.setBookTitle(b.getBook().getTitle());
        info.setIsbn(b.getBook().getIsbn());
        info.setReturnedDate(b.getReturnedDate());
        info.setIsReturned(b.getIsReturned());
        return info;
    }
}