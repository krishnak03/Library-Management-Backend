package com.example.library.management.tool.library.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Admin {
    @JsonProperty("admin_user_id")
    private int adminUserId;
    @JsonProperty("admin_username")
    private String adminUsername;
    @JsonProperty("admin_password")
    private String adminPassword;
}
