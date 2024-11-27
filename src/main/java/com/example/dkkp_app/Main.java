package com.example.dkkp_app;

import com.example.dkkp_app.dao.UserDao;
import com.example.dkkp_app.model.User_Entity;
import com.example.dkkp_app.service.DatabaseService;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Tạo đối tượng User_Entity mới
        User_Entity user = new User_Entity("U007", "John Doe", Date.valueOf("2024-11-27"));

        // Tạo UserDao và lưu đối tượng vào cơ sở dữ liệu
        UserDao userDao = new UserDao();
        userDao.createUser(user);

        System.out.println("User has been added successfully.");

        List<User_Entity> users = userDao.getAllUsers();

        // Hiển thị thông tin các người dùng
        for (User_Entity u : users) {
            System.out.println("ID: " + u.getID_USER() + ", Name: " + u.getNAME_USER() + ", Date Joined: " + u.getDATE_JOIN());
        }
    }
}
