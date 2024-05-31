package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.book.Book;
import com.example.library.management.tool.library.dto.book.BookSearchRequest;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final class BookRowMapper implements RowMapper<Book> {
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

    public ResponseEntity<?> getAllBooks() {
        String getAllBooksQuery = "SELECT * FROM book;";
        try {
            List<Book> books = jdbcTemplate.query(getAllBooksQuery, new BookRowMapper());
            if (books.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(false, "No Books found."), HttpStatus.OK);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error retrieving books: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean genreExists(String genreName) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM genre WHERE genre_name = ?",
                    new Object[]{genreName},
                    Integer.class
            );

            if (count == null || count == 0) {
                // Genre does not exist, insert it into the table
                int rowsAffected = jdbcTemplate.update(
                        "INSERT INTO genre (genre_name) VALUES (?)",
                        genreName
                );
                return rowsAffected > 0;
            } else {
                // Genre exists
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean languageExists(String languageName) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM language WHERE language_name = ?",
                    new Object[]{languageName},
                    Integer.class
            );

            if (count == null || count == 0) {
                // Language does not exist, insert it into the table
                int rowsAffected = jdbcTemplate.update(
                        "INSERT INTO language (language_name) VALUES (?)",
                        languageName
                );
                return rowsAffected > 0;
            } else {
                // Language exists
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse addBook(Book book) {

        boolean genreExists = genreExists(book.getBookGenre());
        boolean languageExists = languageExists(book.getBookLang());

        if (!genreExists || !languageExists) {
            return new ApiResponse(false, "Genre or Language not present in System.");
        }

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
                    book.getBookQuantity(),
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

    public ResponseEntity<?> searchBooks(BookSearchRequest bookSearchRequest) {
        String phrase = bookSearchRequest.getPhrase();
        String genre = bookSearchRequest.getGenre();
        String language = bookSearchRequest.getLanguage();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM book WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (phrase != null && !phrase.isEmpty()) {
            queryBuilder.append("AND (similarity(book_name, ?) > 0.3 OR similarity(book_author, ?) > 0.3) ");
            params.add(phrase);
            params.add(phrase);
        }
        if (genre != null && !genre.isEmpty()) {
            queryBuilder.append("AND book_genre = ? ");
            params.add(genre);
        }
        if (language != null && !language.isEmpty()) {
            queryBuilder.append("AND book_lang = ? ");
            params.add(language);
        }

        queryBuilder.append("ORDER BY book_popularity DESC, GREATEST(similarity(book_name, ?), similarity(book_author, ?)) DESC");
        if (phrase != null && !phrase.isEmpty()) {
            params.add(phrase);
            params.add(phrase);
        }

        String query = queryBuilder.toString();

        try {
            List<Book> books = jdbcTemplate.query(query, params.toArray(), new BookRowMapper());

            if (books.isEmpty()) {
                // If no results found with the phrase and both genre and language are null/empty, return no books found
                if ((genre == null || genre.isEmpty()) && (language == null || language.isEmpty())) {
                    return new ResponseEntity<>(new ApiResponse(false, "No books found."), HttpStatus.OK);
                }

                // If no results found with the phrase, return books filtered by genre and/or language
                queryBuilder = new StringBuilder("SELECT * FROM book WHERE 1=1 ");
                params.clear();

                if (genre != null && !genre.isEmpty()) {
                    queryBuilder.append("AND book_genre = ? ");
                    params.add(genre);
                }
                if (language != null && !language.isEmpty()) {
                    queryBuilder.append("AND book_lang = ? ");
                    params.add(language);
                }

                if (params.isEmpty()) {
                    return new ResponseEntity<>(new ApiResponse(false, "No books found."), HttpStatus.OK);
                }

                queryBuilder.append("ORDER BY book_popularity DESC");

                String fallbackQuery = queryBuilder.toString();

                books = jdbcTemplate.query(fallbackQuery, params.toArray(), new BookRowMapper());

                if (books.isEmpty()) {
                    return new ResponseEntity<>(new ApiResponse(false, "No books found."), HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error occurred while searching for books: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
