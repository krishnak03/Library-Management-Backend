package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.AdminDao;
import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    public AdminDao adminDao;

    public ResponseEntity<?> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    public ApiResponse addAdmin(Admin admin){
        if (ValidatorUtil.isEmptyOrNull(admin.getAdminUsername()) ||
                ValidatorUtil.isEmptyOrNull(admin.getAdminPassword())) {
            return new ApiResponse(false, "Admin username and admin password shouldn't be empty or null.");
        }
        return adminDao.addAdmin(admin);
    }

    public ApiResponse updateAdmin(Admin admin) {
        if (admin.getAdminUserId() == null) {
            return new ApiResponse(false, "Admin User Id should not be null");
        }
        if ((ValidatorUtil.isEmptyOrNull(admin.getAdminUsername()) ||
                ValidatorUtil.isEmptyOrNull(admin.getAdminPassword()))) {
            return new ApiResponse(false, "Admin username and admin password(both) shouldn't be empty or null.");
        }
        return adminDao.updateAdmin(admin);
    }

    public ApiResponse deleteAdmin(Integer adminId) {
        if (adminId == null) {
            return new ApiResponse(false, "Admin id shouldn't be null.");
        }
        return adminDao.deleteAdmin(adminId);
    }

    public ApiResponse loginAdmin(String adminUsername, String adminPassword) {
        if (ValidatorUtil.isEmptyOrNull(adminUsername) || ValidatorUtil.isEmptyOrNull(adminPassword)) {
            return new ApiResponse(false, "Admin username and admin password shouldn't be null to login.");
        }
        return adminDao.loginAdmin(adminUsername, adminPassword);
    }
}
