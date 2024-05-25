package com.example.library.management.tool.library.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_phone")
    private String userPhone;
    @JsonProperty("user_email")
    private String userEmail;
}
