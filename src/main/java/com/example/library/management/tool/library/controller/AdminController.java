package com.example.library.management.tool.library.controller;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping
    public ApiResponse addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @PutMapping
    public ApiResponse updateAdmin(@RequestBody Admin admin) {
        return adminService.updateAdmin(admin);
    }

    @DeleteMapping
    public ApiResponse deleteAdmin(@RequestBody Admin admin) {
        return adminService.deleteAdmin(admin.getAdminUserId());
    }
}
