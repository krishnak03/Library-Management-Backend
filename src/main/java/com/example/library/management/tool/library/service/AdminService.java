package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.AdminDao;
import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    public AdminDao adminDao;

    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }

    public ApiResponse addAdmin(Admin admin){
        if (Validator.isEmptyOrNull(admin.getAdminUsername()) ||
                Validator.isEmptyOrNull(admin.getAdminPassword())) {
            return new ApiResponse(false, "Admin username and admin password shouldn't be empty or null.");
        }
        return adminDao.addAdmin(admin);
    }

    public ApiResponse updateAdmin(Admin admin) {
        if (Validator.isEmptyOrNull(admin.getAdminUsername()) &&
                Validator.isEmptyOrNull(admin.getAdminPassword())) {
            return new ApiResponse(false, "Admin username and admin password(both) shouldn't be empty or null.");
        }
        return adminDao.updateAdmin(admin);
    }

    public ApiResponse deleteAdmin(int adminId) {
        if (adminId == 0) {
            return new ApiResponse(false, "Admin id shouldn't be null.");
        }
        return adminDao.deleteAdmin(adminId);
    }
}
