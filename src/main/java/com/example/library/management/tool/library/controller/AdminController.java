package com.example.library.management.tool.library.controller;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.admin.LoginAdmin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.AdminService;
import com.example.library.management.tool.library.util.DecryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping
    public ResponseEntity<?> getAllAdmins() {
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

    @PostMapping(path = "/login")
    public ApiResponse loginAdmin(@RequestBody LoginAdmin loginAdmin) {
        String decryptedUsername = DecryptUtil.decrypt(loginAdmin.getAdminUsername());
        String decryptedPassword = DecryptUtil.decrypt(loginAdmin.getAdminPassword());
        return adminService.loginAdmin(decryptedUsername, decryptedPassword);
    }

    @GetMapping(path = "/secret-key")
    public static String generateSecretKey() {
        try {
            // Create a KeyGenerator instance for AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // Specify the key size (128, 192, or 256 bits)
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Error while generating secret key", e);
        }
    }
}
