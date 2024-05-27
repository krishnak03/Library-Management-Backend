package com.example.library.management.tool.library.dto.borrow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    @JsonProperty("borrow_id")
    private Integer borrowId;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("issue_date")
    private String issueDate;

    @JsonProperty("return_date")
    private String returnDate;

    @JsonProperty("fine")
    private int fine;
}
