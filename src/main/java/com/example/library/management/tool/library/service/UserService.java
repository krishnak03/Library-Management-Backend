package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.UserDao;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.dto.user.User;
import com.example.library.management.tool.library.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public ResponseEntity<?> getAllUsers() {
        return userDao.getAllUsers();
    }

    public ApiResponse addUser(User user) {

        if (ValidatorUtil.isEmptyOrNull(user.getUserName()) ||
        ValidatorUtil.isEmptyOrNull(user.getUserPhone()) ||
        ValidatorUtil.isEmptyOrNull(user.getUserEmail())) {
            return new ApiResponse(false, "User Name, User Phone and User Email shouldn't be empty or null.");
        }

        return userDao.addUser(user);
    }

    public ApiResponse updateUser(User user) {
        if (user.getUserId() == null) {
            return new ApiResponse(false, "User id shouldn't be null.");
        }
        if (ValidatorUtil.isEmptyOrNull(user.getUserName()) &&
                ValidatorUtil.isEmptyOrNull(user.getUserEmail()) &&
                ValidatorUtil.isEmptyOrNull(user.getUserPhone())) {
            return new ApiResponse(false, "User Name, User phone, and User email (all) shouldn't be empty or null.");
        }
        return userDao.updateUser(user);
    }

    public ApiResponse deleteUser(Integer userId) {
        if (userId == null) {
            return new ApiResponse(false, "User id shouldn't be null.");
        }
        return userDao.deleteUser(userId);
    }
}
