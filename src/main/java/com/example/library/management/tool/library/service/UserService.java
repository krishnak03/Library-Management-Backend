package com.example.library.management.tool.library.service;

import com.example.library.management.tool.library.dao.UserDao;
import com.example.library.management.tool.library.dto.genre.Genre;
import com.example.library.management.tool.library.dto.standardresponse.ApiResponse;
import com.example.library.management.tool.library.dto.user.User;
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
        return userDao.addUser(user);
    }

    public ApiResponse updateUser(User user) {
        return userDao.updateUser(user);
    }

    public ApiResponse deleteUser(int userId){
        return userDao.deleteUser(userId);
    }
}
