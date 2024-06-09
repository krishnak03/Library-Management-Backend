package com.example.library.management.tool.library.controller;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.admin.LoginAdmin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.service.AdminService;
import com.example.library.management.tool.library.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

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
        
        String decryptedUsername = EncryptionUtil.decrypt(loginAdmin.getAdminUsername());
        if(EncryptionUtil.decrypt(loginAdmin.getAdminUsername()).equalsIgnoreCase("Error while decrypting value.") ||
                (loginAdmin.getAdminUsername() != null && decryptedUsername == null)) {
            return new ApiResponse(false, "Error while decrypting username");
        }
        String decryptedPassword = EncryptionUtil.decrypt(loginAdmin.getAdminPassword());
        if(EncryptionUtil.decrypt(loginAdmin.getAdminUsername()).equalsIgnoreCase("Error while decrypting value.") ||
                (loginAdmin.getAdminPassword() != null && decryptedPassword == null)) {
            return new ApiResponse(false, "Error while decrypting password");
        }
        return adminService.loginAdmin(decryptedUsername, decryptedPassword);
    }

    @GetMapping(path = "/secret-key")
    public static String generateSecretKey() {
        try {
            // Create a KeyGenerator instance for AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Error while generating secret key", e);
        }
    }
}
