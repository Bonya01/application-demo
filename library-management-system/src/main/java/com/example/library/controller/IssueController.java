package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.entity.IssueRecord;
import com.example.library.service.IssueService;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/issue")
    public String issueBook(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam(defaultValue = "14") int days, Model model) {
        try {
            IssueRecord record = issueService.issueBook(userId, bookId, days);
            return "redirect:/books";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "books/list";
        }
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam Long issueId, Model model) {
        try {
            IssueRecord record = issueService.returnBook(issueId);
            return "redirect:/books";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "books/list";
        }
    }
}
