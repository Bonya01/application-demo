package com.example.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.service.BookService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("book", new BookDto());
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") BookDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "books/add";
        }
        bookService.create(dto);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        BookDto dto = new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalCopies(),
                book.getDescription()
        );
        model.addAttribute("book", dto);
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @Valid @ModelAttribute("book") BookDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        bookService.update(id, dto);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q, Model model) {
        if (q == null || q.isBlank()) {
            return "redirect:/books";
        }
        model.addAttribute("books", bookService.search(q));
        return "books/list";
    }
}
