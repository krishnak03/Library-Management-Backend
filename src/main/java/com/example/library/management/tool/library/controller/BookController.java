package com.example.library.management.tool.library.controller;


import com.example.library.management.tool.library.dto.book.Book;
import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.BookService;
import com.example.library.management.tool.library.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    public BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {

        return bookService.getAllBooks();
    }

    @PostMapping
    public ApiResponse addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public ApiResponse updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping
    public ApiResponse deleteBook(@RequestBody Book book) {
        return bookService.deleteBook(book.getBookId());
    }
}
