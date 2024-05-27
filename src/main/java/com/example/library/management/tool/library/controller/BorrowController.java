package com.example.library.management.tool.library.controller;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.borrow.Borrow;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping
    public List<Borrow> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    @PostMapping
    public ApiResponse addBorrow(@RequestBody Borrow borrow) {
        return borrowService.addBorrow(borrow);
    }

    @PutMapping
    public ApiResponse updateAdmin(@RequestBody Borrow borrow) {
        return borrowService.updateBorrow(borrow);
    }
}
