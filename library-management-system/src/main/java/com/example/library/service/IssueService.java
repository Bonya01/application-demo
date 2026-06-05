package com.example.library.service;

import com.example.library.entity.IssueRecord;

public interface IssueService {
    IssueRecord issueBook(Long userId, Long bookId, int days);
    IssueRecord returnBook(Long issueId);
}
