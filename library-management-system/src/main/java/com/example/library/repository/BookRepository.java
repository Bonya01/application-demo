package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Book> search(@Param("q") String query);
}
