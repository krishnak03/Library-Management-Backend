package com.example.library.management.tool.library.dao;

import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers() {
        String getAllUsersQuery = "SELECT * FROM \"user\";";
        try {
            return jdbcTemplate.query(getAllUsersQuery, new UserRowMapper());
        } catch (Exception e) {
            System.out.println("Error retrieving users: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserEmail(rs.getString("user_email"));
            user.setUserPhone(rs.getString("user_phone"));
            return user;
        }
    }

    public ApiResponse addUser(User user) {

        String userName = user.getUserName();
        String userEmail = user.getUserEmail();
        String userPhone = user.getUserPhone();
        String addUserQuery = "INSERT INTO \"user\" (user_name, user_email, user_phone) VALUES (?, ?, ?);";
        try {
            int rowsAffected = jdbcTemplate.update(addUserQuery, userName, userEmail, userPhone);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "User added successfully.");
            } else {
                return new ApiResponse(false, "Error while adding user.");
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error adding user: " + e.getMessage());
        }
    }

    public ApiResponse updateUser(User user) {
        String updateUserQuery = """
                UPDATE "user"
                SET user_name = COALESCE(?, user_name),
                    user_email = COALESCE(?, user_email),
                    user_phone = COALESCE(?, user_phone)
                WHERE user_id = ?;""";
        try {
            int rowsAffected = jdbcTemplate.update(updateUserQuery, user.getUserName(), user.getUserEmail(), user.getUserPhone(), user.getUserId());
            if (rowsAffected > 0) {
                return new ApiResponse(true, "User updated successfully.");
            } else {
                return new ApiResponse(false, "No user found with ID: " + user.getUserId());
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error updating user: " + e.getMessage());
        }
    }

    public ApiResponse deleteUser(int userId) {
        String deleteUserQuery = "DELETE FROM \"user\" WHERE user_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(deleteUserQuery, userId);
            if (rowsAffected > 0) {
                return new ApiResponse(true, "User deleted successfully.");
            } else {
                return new ApiResponse(false, "No user found with ID: " + userId);
            }
        } catch (Exception e) {
            return new ApiResponse(false, "Error deleting user: " + e.getMessage());
        }
    }
}

