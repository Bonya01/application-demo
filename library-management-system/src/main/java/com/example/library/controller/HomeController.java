package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.library.repository.BookRepository;
import com.example.library.repository.IssueRecordRepository;
import com.example.library.repository.UserRepository;

@Controller
public class HomeController {
    private final BookRepository bookRepository;
    private final IssueRecordRepository issueRecordRepository;
    private final UserRepository userRepository;

    public HomeController(BookRepository bookRepository, IssueRecordRepository issueRecordRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.issueRecordRepository = issueRecordRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalBooks", bookRepository.count());
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("issuedBooks", issueRecordRepository.count());
        return "dashboard";
    }
}
