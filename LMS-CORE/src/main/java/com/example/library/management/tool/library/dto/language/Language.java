package com.example.library.management.tool.library.dto.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Language {

    @JsonProperty("language_id")
    private Integer languageId;
    @JsonProperty("language_name")
    private String languageName;
}
