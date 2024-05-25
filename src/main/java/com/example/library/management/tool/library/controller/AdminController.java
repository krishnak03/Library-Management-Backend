package com.example.library.management.tool.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {

    @GetMapping
    public String returnAdmin() {
        return "Admin was found.";
    }
}
