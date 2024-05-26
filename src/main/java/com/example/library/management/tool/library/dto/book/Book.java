package com.example.library.management.tool.library.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Book {

    @JsonProperty("book_id")
    private int bookId;
    @JsonProperty("book_name")
    private String bookName;
    @JsonProperty("book_author")
    private String bookAuthor;
    @JsonProperty("book_genre")
    private String bookGenre;
    @JsonProperty("book_lang")
    private String bookLang;
    @JsonProperty("book_shelf_id")
    private String bookShelfId;
    @JsonProperty("book_quantity")
    private Integer bookQuantity;
    @JsonProperty("book_available")
    private Integer bookAvailable;
    @JsonProperty("book_popularity")
    private Integer bookPopularity;

}
