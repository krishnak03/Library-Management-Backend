package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.book.Book;
import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Book> getAllBooks() {
        String getAllBooksQuery = "SELECT * FROM book;";
        try {
            return jdbcTemplate.query(getAllBooksQuery, new BookRowMapper());
        } catch (Exception e) {
            System.out.println("Error retrieving books: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setBookName(rs.getString("book_name"));
            book.setBookAuthor(rs.getString("book_author"));
            book.setBookGenre(rs.getString("book_genre"));
            book.setBookLang(rs.getString("book_lang"));
            book.setBookShelfId(rs.getString("book_shelf_id"));
            book.setBookQuantity(rs.getInt("book_quantity"));
            book.setBookAvailable(rs.getInt("book_available"));
            book.setBookPopularity(rs.getInt("book_popularity"));
            return book;
        }
    }

    private int ensureGenreExists(String genreName) {
        try {
            Integer genreId = jdbcTemplate.queryForObject("SELECT genre_id FROM genre WHERE genre_name = ?", new Object[]{genreName}, Integer.class);
            return genreId;
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update("INSERT INTO genre (genre_name) VALUES (?)", genreName);
            return jdbcTemplate.queryForObject("SELECT genre_id FROM genre WHERE genre_name = ?", new Object[]{genreName}, Integer.class);
        }
    }

    private int ensureLanguageExists(String languageName) {
        try {
            Integer languageId = jdbcTemplate.queryForObject("SELECT language_id FROM language WHERE language_name = ?", new Object[]{languageName}, Integer.class);
            return languageId;
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update("INSERT INTO language (language_name) VALUES (?)", languageName);
            return jdbcTemplate.queryForObject("SELECT language_id FROM language WHERE language_name = ?", new Object[]{languageName}, Integer.class);
        }
    }

    public ApiResponse addBook(Book book) {

        int genreId = ensureGenreExists(book.getBookGenre());
        int languageId = ensureLanguageExists(book.getBookLang());

        String insertQuery = "INSERT INTO book (book_name, book_author, book_genre, book_lang, book_shelf_id, book_quantity, book_available, book_popularity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            int rowsAffected = jdbcTemplate.update(insertQuery,
                    book.getBookName(),
                    book.getBookAuthor(),
                    book.getBookGenre(),
                    book.getBookLang(),
                    book.getBookShelfId(),
                    book.getBookQuantity(),
                    book.getBookAvailable(),
                    book.getBookPopularity());
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Book added successfully.");
            } else {
                return new ApiResponse(false, "Error while adding book.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error adding book: " + e.getMessage());
        }
    }

    public ApiResponse updateBook(Book book) {
        String updateBookQuery = """
                UPDATE book
                SET book_name = COALESCE(?, book_name),
                    book_author = COALESCE(?, book_author),
                    book_genre = COALESCE(?, book_genre),
                    book_lang = COALESCE(?, book_lang),
                    book_shelf_id = COALESCE(?, book_shelf_id),
                    book_quantity = COALESCE(?, book_quantity),
                    book_available = COALESCE(?, book_available),
                    book_popularity = COALESCE(?, book_popularity)
                WHERE book_id = ?;""";
        try {
            int rowsAffected = jdbcTemplate.update(updateBookQuery,
                    book.getBookName(),
                    book.getBookAuthor(),
                    book.getBookGenre(),
                    book.getBookLang(),
                    book.getBookShelfId(),
                    book.getBookQuantity(),
                    book.getBookAvailable(),
                    book.getBookPopularity(),
                    book.getBookId());
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Book updated successfully.");
            } else {
                return new ApiResponse(false, "No book found with ID: " + book.getBookId());
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error updating book: " + e.getMessage());
        }
    }

    public ApiResponse deleteBook(int bookId) {
        String deleteBookQuery = "DELETE FROM book WHERE book_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(deleteBookQuery, bookId);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "Book deleted successfully.");
            } else {
                return new ApiResponse(false, "No book found with ID: " + bookId);
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error deleting book: " + e.getMessage());
        }
    }
}
