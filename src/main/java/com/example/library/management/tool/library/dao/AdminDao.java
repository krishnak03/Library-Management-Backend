package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.admin.Admin;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.exceptions.CustomLibraryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
            Admin admin = new Admin();
            admin.setAdminUserId(rs.getInt("admin_user_id"));
            admin.setAdminUsername(rs.getString("admin_username"));
            admin.setAdminPassword(rs.getString("admin_password"));
            return admin;
        }
    }

    public ResponseEntity<?> getAllAdmins() {
        String getAllAdminsQuery = "SELECT * FROM \"admin\";";
        try {
            List<Admin> admins = jdbcTemplate.query(getAllAdminsQuery, new AdminRowMapper());
            if (admins.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(false, "No Admins found."), HttpStatus.OK);
            }
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Exception occurred while retrieving all Admins: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse addAdmin(Admin admin) {

        String addAdminQuery = "INSERT INTO \"admin\" (admin_username, admin_password) VALUES (?, ?);";
        try {
            int affectedAdminData = jdbcTemplate.update(addAdminQuery, admin.getAdminUsername(), admin.getAdminPassword());
            if (affectedAdminData > 0) {
                return new ApiResponse(true, "Admin created successfully.");
            } else {
                return new ApiResponse(false, "Error occurred while creating new Admin.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while Adding Admin. " + e.getMessage());
        }
    }

    public ApiResponse updateAdmin(Admin admin) {
        String updateAdminQuery = """
                UPDATE "admin"
                SET admin_username = COALESCE(?, admin_username),
                    admin_password = COALESCE(?, admin_password)
                WHERE admin_user_id = ?;""";
        try {
            int affectedAdminData = jdbcTemplate.update(updateAdminQuery,
                    admin.getAdminUsername(),
                    admin.getAdminPassword(),
                    admin.getAdminUserId());
            if (affectedAdminData > 0) {
                return new ApiResponse(true, "Admin data updated successfully.");
            } else{
                return new ApiResponse(false, "Error occurred while updating Admin.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while updating Admin. " + e.getMessage());
        }
    }

    public ApiResponse deleteAdmin(int adminId) {
        String deleteAdminQuery = "DELETE FROM \"admin\" WHERE admin_user_id = ?;";
        try {
            int affectedAdminData = jdbcTemplate.update(deleteAdminQuery, adminId);
            if (affectedAdminData > 0) {
                return new ApiResponse(true, "Admin with id number: " + adminId + " deleted successfully.");
            } else {
                return new ApiResponse(false, "Error occurred while deleting Admin of id number: " + adminId);
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while deleting Admin. " + e.getMessage());
        }
    }

    public ApiResponse loginAdmin(String adminUsername, String adminPassword) {
        String loginAdminQuery = """
            SELECT CASE
                WHEN EXISTS (
                    SELECT 1
                    FROM "admin"
                    WHERE admin_username = ? AND admin_password = ?
                )
                THEN true
                ELSE false
            END;
        """;
        try {
            Boolean loginSuccessful = jdbcTemplate.queryForObject(loginAdminQuery, Boolean.class, adminUsername, adminPassword);
            if (loginSuccessful != null && loginSuccessful) {
                return new ApiResponse(true, "Login successful.");
            } else {
                return new ApiResponse(false, "Login failed.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Exception occurred while logging in Admin. " + e.getMessage());
        }
    }
}
