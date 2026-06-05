package com.example.library.service.impl;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
@Primary
public class BookJdbcServiceImpl implements BookService {
    private final JdbcTemplate jdbc;

    public BookJdbcServiceImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Book> rowMapper = new RowMapper<>() {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("total_copies"),
                    rs.getInt("available_copies"),
                    rs.getString("description")
            );
        }
    };

    @Override
    public Book create(BookDto dto) {
        String sql = "INSERT INTO books (title, author, isbn, total_copies, available_copies, description) VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getTitle());
            ps.setString(2, dto.getAuthor());
            ps.setString(3, dto.getIsbn());
            ps.setInt(4, dto.getTotalCopies());
            ps.setInt(5, dto.getTotalCopies());
            ps.setString(6, dto.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) throw new RuntimeException("Failed to insert book");
        return findById(key.longValue());
    }

    @Override
    public Book update(Long id, BookDto dto) {
        Book existing = findById(id);
        int diff = dto.getTotalCopies() - existing.getTotalCopies();
        int newAvailable = existing.getAvailableCopies() + diff;
        if (newAvailable < 0) newAvailable = 0;

        String sql = "UPDATE books SET title=?, author=?, isbn=?, total_copies=?, available_copies=?, description=? WHERE id=?";
        jdbc.update(sql,
                dto.getTitle(),
                dto.getAuthor(),
                dto.getIsbn(),
                dto.getTotalCopies(),
                newAvailable,
                dto.getDescription(),
                id
        );
        return findById(id);
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM books WHERE id = ?", id);
    }

    @Override
    public Book findById(Long id) {
        return jdbc.queryForObject("SELECT id, title, author, isbn, total_copies, available_copies, description FROM books WHERE id = ?", new Object[]{id}, rowMapper);
    }

    @Override
    public List<Book> search(String q) {
        String like = "%" + q.toLowerCase() + "%";
        return jdbc.query("SELECT id, title, author, isbn, total_copies, available_copies, description FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? OR LOWER(isbn) LIKE ?", new Object[]{like, like, like}, rowMapper);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT id, title, author, isbn, total_copies, available_copies, description FROM books ORDER BY id", rowMapper);
    }
}
