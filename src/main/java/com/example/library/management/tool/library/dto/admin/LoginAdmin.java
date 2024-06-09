package com.example.library.management.tool.library.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginAdmin {

    @JsonProperty("admin_username")
    private String adminUsername;

    @JsonProperty("admin_password")
    private String adminPassword;
}
