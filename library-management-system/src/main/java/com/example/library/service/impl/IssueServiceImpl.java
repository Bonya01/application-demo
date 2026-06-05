package com.example.library.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.library.entity.Book;
import com.example.library.entity.IssueRecord;
import com.example.library.entity.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.IssueRecordRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.IssueService;

@Service
public class IssueServiceImpl implements IssueService {
    private final IssueRecordRepository issueRecordRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final double finePerDay = 1.0; // configurable later

    public IssueServiceImpl(IssueRecordRepository issueRecordRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.issueRecordRepository = issueRecordRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public IssueRecord issueBook(Long userId, Long bookId, int days) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(days);

        IssueRecord record = new IssueRecord(null, user, book, issueDate, dueDate, null, 0.0);
        return issueRecordRepository.save(record);
    }

    @Override
    public IssueRecord returnBook(Long issueId) {
        IssueRecord record = issueRecordRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue record not found"));
        if (record.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }
        record.setReturnDate(LocalDate.now());
        if (record.getReturnDate().isAfter(record.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());
            double fine = daysLate * finePerDay;
            record.setFine(fine);
        }
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        return issueRecordRepository.save(record);
    }
}
