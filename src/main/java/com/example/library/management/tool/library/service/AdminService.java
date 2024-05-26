package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.AdminDao;
import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
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
        return adminDao.addAdmin(admin);
    }

    public ApiResponse updateAdmin(Admin admin){
        return adminDao.updateAdmin(admin);
    }

    public  ApiResponse deleteAdmin(int adminId){
        return adminDao.deleteAdmin(adminId);
    }
}
