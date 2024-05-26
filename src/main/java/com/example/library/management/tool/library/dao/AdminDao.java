package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class AdminRowMapper implements RowMapper<Admin> {
        @Override
        public Admin mapRow(ResultSet resultSet , int rowNo) throws SQLException {
            Admin admin = new Admin();
            admin.setAdminUserId(resultSet.getInt("admin_user_id"));
            admin.setAdminUsername(resultSet.getString("admin_username"));
            admin.setAdminPassword(resultSet.getString("admin_password"));
            return admin;
        }
    }

    public List<Admin> getAllAdmins() {
        String getAllAdminsQuery = "SELECT * FROM \"admin\";";
        try {
            return jdbcTemplate.query(getAllAdminsQuery, new AdminRowMapper());
        } catch (Exception e) {
            System.out.println("Exception occurred while retrieving all Admins" + e.getMessage());
            return Collections.emptyList();
        }
    }

    public ApiResponse addAdmin(Admin admin) {
        String addAdminQuery = "INSERT INTO admin (admin_user_id, admin_username, admin_password) VALUES (?, ? , ?);";
        try {
            int affectedAdminData = jdbcTemplate.update(addAdminQuery , admin.getAdminUserId(),admin.getAdminUsername(),admin.getAdminPassword());
            if (affectedAdminData > 0) {
                return new ApiResponse(true, "Admin created successfully.");
            }else {
                return new ApiResponse(false, "Error occurred while creating new Admin.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while Adding Admin. " + e.getMessage());
        }
    }

    public ApiResponse updateAdmin(Admin admin) {
        String updateAdminQuery = "UPDATE admin SET admin_user_id = ?, admin_username = ? WHERE admin_password = ?";
        try {
            int affectedAdminData = jdbcTemplate.update(updateAdminQuery, admin.getAdminUserId(),admin.getAdminUsername(),admin.getAdminPassword());
            if(affectedAdminData > 0) {
                return new ApiResponse(true, "Admin data updated successfully.");
            }else{
                return new ApiResponse(false, "Error occurred while updating Admin.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while updating Admin. " + e.getMessage());
        }
    }

    public ApiResponse deleteAdmin(int adminId) {
        String deleteAdminQuery = "DELETE FROM admin WHERE admin_user_id = ?;";
        try {
            int affectedAdminData = jdbcTemplate.update(deleteAdminQuery,adminId);
            if(affectedAdminData > 0) {
                return new ApiResponse(true, "Admin with id number: " + adminId + " deleted successfully.");
            } else {
                return new ApiResponse(false, "Error occurred while deleting Admin of id number: " + adminId);
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while deleting Admin. " + e.getMessage());
        }
    }
}
