package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;

import java.util.List;

public interface BookService {
    Book create(BookDto dto);
    Book update(Long id, BookDto dto);
    void delete(Long id);
    Book findById(Long id);
    List<Book> search(String q);
    List<Book> findAll();
}
