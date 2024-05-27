package com.example.library.management.tool.library.controller;

import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.dto.user.User;
import com.example.library.management.tool.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ApiResponse addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public ApiResponse updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping
    public ApiResponse deleteUser(@RequestBody User user) {
        return userService.deleteUser(user.getUserId());
    }
}
