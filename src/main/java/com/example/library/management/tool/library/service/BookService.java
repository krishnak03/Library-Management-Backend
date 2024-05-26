package com.example.library.management.tool.library.service;


import com.example.library.management.tool.library.dao.BookDao;
import com.example.library.management.tool.library.dto.book.Book;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    public BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public ApiResponse addBook(Book book) {
        if (Validator.isEmptyOrNull(book.getBookName())) {
            return new ApiResponse(false, "Book name shouldn't be empty or null.");
        }
        if (Validator.isEmptyOrNull(book.getBookAuthor())) {
            return new ApiResponse(false, "Book author shouldn't be empty or null.");
        }
        if (Validator.isEmptyOrNull(book.getBookGenre())) {
            return new ApiResponse(false, "Book genre shouldn't be empty or null.");
        }
        if (Validator.isEmptyOrNull(book.getBookLang())) {
            return new ApiResponse(false, "Book language shouldn't be empty or null.");
        }
        return bookDao.addBook(book);
    }

    public ApiResponse updateBook(Book book) {
        if (book.getBookId() <= 0) {
            return new ApiResponse(false, "Invalid book ID.");
        }
        if (Validator.isEmptyOrNull(book.getBookName()) && Validator.isEmptyOrNull(book.getBookAuthor()) &&
                Validator.isEmptyOrNull(book.getBookGenre()) && Validator.isEmptyOrNull(book.getBookLang())) {
            return new ApiResponse(false, "No valid update information provided.");
        }
        return bookDao.updateBook(book);
    }

    public ApiResponse deleteBook(int bookId) {
        if (bookId <= 0) {
            return new ApiResponse(false, "Invalid book ID provided.");
        }
        return bookDao.deleteBook(bookId);
    }


}
