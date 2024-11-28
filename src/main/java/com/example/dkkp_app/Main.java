package com.example.dkkp_app;

import com.example.dkkp_app.dao.UserDao;
import com.example.dkkp_app.model.User_Entity;
import com.example.dkkp_app.service.UserService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        // Tạo đối tượng User_Entity mới
//        User_Entity user = new User_Entity("U1232", "John Doe", Date.valueOf("2024-11-27"));
//
//        // Tạo UserDao và lưu đối tượng vào cơ sở dữ liệu
//        UserDao userDao = new UserDao();
//        userDao.createUser(user);
//
//        System.out.println("User has been added successfully.");
//
//        List<User_Entity> users = userDao.getAllUsers();
//
//        // Hiển thị thông tin các người dùng
//        for (User_Entity u : users) {
//            System.out.println("ID: " + u.getID_USER() + ", Name: " + u.getNAME_USER() + ", Date Joined: " + u.getDATE_JOIN());
//        }

        // Tạo đối tượng UserService
        UserService userService = new UserService();

        // Nhập tên người dùng từ bàn phím (hoặc JavaFX input)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user name to search: ");
        String userName = scanner.nextLine();

        // Lấy danh sách người dùng theo tên
        List<User_Entity> users = userService.getUsersByName(userName);

        // Hiển thị kết quả
        if (users.isEmpty()) {
            System.out.println("No users found with the name: " + userName);
        } else {
            for (User_Entity user : users) {
                System.out.println("ID: " + user.getID_USER() + ", Name: " + user.getNAME_USER() + ", Date Joined: " + user.getDATE_JOIN());
            }
        }
    }

}
