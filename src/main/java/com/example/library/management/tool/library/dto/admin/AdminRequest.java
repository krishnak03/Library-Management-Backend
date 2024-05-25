package com.example.library.management.tool.library.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminRequest {
    @JsonProperty("genre_id")
    private int genreId;
    @JsonProperty("genre_name")
    private String genreName;
}
