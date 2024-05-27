package com.example.library.management.tool.library.service;


import com.example.library.management.tool.library.dao.BookDao;
import com.example.library.management.tool.library.dto.book.Book;
import com.example.library.management.tool.library.dto.book.BookSearchRequest;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    public BookDao bookDao;

    public ResponseEntity<?> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public ApiResponse addBook(Book book) {
        if (ValidatorUtil.isEmptyOrNull(book.getBookName()) ||
                ValidatorUtil.isEmptyOrNull(book.getBookAuthor()) ||
                ValidatorUtil.isEmptyOrNull(book.getBookGenre()) ||
                ValidatorUtil.isEmptyOrNull(book.getBookLang())) {
            return new ApiResponse(false
                    , "Book name, Book author, Book genre and Book language shouldn't be empty or null.");
        }

        return bookDao.addBook(book);
    }

    public ApiResponse updateBook(Book book) {
        if (book.getBookId() == null ) {
            return new ApiResponse(false, "Book id shouldn't be null.");
        }
        if (ValidatorUtil.isEmptyOrNull(book.getBookName()) &&
                ValidatorUtil.isEmptyOrNull(book.getBookAuthor()) &&
                ValidatorUtil.isEmptyOrNull(book.getBookGenre()) &&
                ValidatorUtil.isEmptyOrNull(book.getBookLang())) {
            return new ApiResponse(false, "No valid update information provided.");
        }
        return bookDao.updateBook(book);
    }

    public ApiResponse deleteBook(Integer bookId) {
        if (bookId == null ) {
            return new ApiResponse(false, "Book id shouldn't be null.");
        }
        return bookDao.deleteBook(bookId);
    }

    public ResponseEntity<?> searchBooks(BookSearchRequest bookSearchRequest) {
        return bookDao.searchBooks(bookSearchRequest);
    }

}
