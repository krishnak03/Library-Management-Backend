package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.UserDao;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.dto.user.User;
import com.example.library.management.tool.library.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public ApiResponse addUser(User user) {

        if (Validator.isEmptyOrNull(user.getUserName()) ||
        Validator.isEmptyOrNull(user.getUserPhone()) ||
        Validator.isEmptyOrNull(user.getUserEmail())) {
            return new ApiResponse(false, "User Name, User Phone and User Email shouldn't be empty or null.");
        }

        return userDao.addUser(user);
    }

    public ApiResponse updateUser(User user) {
        if (Validator.isEmptyOrNull(user.getUserName()) &&
                Validator.isEmptyOrNull(user.getUserEmail()) &&
                Validator.isEmptyOrNull(user.getUserPhone())) {
            return new ApiResponse(false, "User Name, User phone, and User email (all) shouldn't be empty or null.");
        }
        return userDao.updateUser(user);
    }

    public ApiResponse deleteUser(int userId) {
        if (userId == 0) {
            return new ApiResponse(false, "User id shouldn't be null.");
        }
        return userDao.deleteUser(userId);
    }
}
