package com.example.library.management.tool.library.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {

    @JsonProperty("phrase")
    private String phrase;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("language")
    private String language;
}
