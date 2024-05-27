package com.example.library.management.tool.library.dto.genre;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Genre {
    @JsonProperty("genre_id")
    private Integer genreId;
    @JsonProperty("genre_name")
    private String genreName;
}
