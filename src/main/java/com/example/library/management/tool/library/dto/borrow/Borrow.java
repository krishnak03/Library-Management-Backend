package com.example.library.management.tool.library.dto.borrow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    @JsonProperty("borrow_id")
    private int borrowId;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("book_id")
    private int bookId;

    @JsonProperty("issue_date")
    private String issueDate;

    @JsonProperty("return_date")
    private String returnDate;

    @JsonProperty("fine")
    private int fine;
}
