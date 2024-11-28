package com.example.dkkp_app.service;

import com.example.dkkp_app.dao.UserDao;
import com.example.dkkp_app.model.User_Entity;

import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();  // Khởi tạo UserDao
    }

    // Phương thức truy vấn người dùng theo tên và xử lý logic nghiệp vụ (nếu cần)
    public List<User_Entity> getUsersByName(String name) {
        // Kiểm tra tên hợp lệ
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        // Gọi DAO để lấy người dùng theo tên
        return userDao.getUsersByName(name);
    }

//    UserService có thể gọi UserDao để thực hiện các thao tác liên quan đến người dùng.
//    AdminService có thể cũng sử dụng UserDao để thực hiện các thao tác quản lý người dùng,
//    ví dụ như khóa tài khoản người dùng, mà không cần tạo một UserDao riêng biệt.
}
