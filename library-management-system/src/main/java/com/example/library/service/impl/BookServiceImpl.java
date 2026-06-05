package com.example.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(BookDto dto) {
        Book book = new Book(null,
                dto.getTitle(),
                dto.getAuthor(),
                dto.getIsbn(),
                dto.getTotalCopies(),
                dto.getTotalCopies(),
                dto.getDescription());
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookDto dto) {
        Book book = findById(id);
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        int diff = dto.getTotalCopies() - book.getTotalCopies();
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(book.getAvailableCopies() + diff);
        book.setDescription(dto.getDescription());
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<Book> search(String q) {
        return bookRepository.search(q);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
