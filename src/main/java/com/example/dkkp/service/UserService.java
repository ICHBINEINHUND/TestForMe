package com.example.dkkp.service;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.Report_Bug;
import com.example.dkkp.model.User_Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User_Entity getUsersByID(String id) {
        // chạy được
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID Cannot Be Null!");
        }
        return userDao.getUsersByID(id);
    }

    public boolean registerNewUser(User_Entity user) {
        //chạy được
        // add check ( từ user lấy các property để kiểm tra nếu đạt
        // thì gọi return userDao.createUser(user);
        LocalDateTime DATE_JOIN = LocalDateTime.now();
        User_Entity userC = user;
        return userDao.createUser(user);
    }


    public boolean login(String EMAIL_ACC, String PASSWORD_ACC) {
        //add check
        // chạy được
        // sửa lại các điều kiện kiểm tra email và pass
        if(EMAIL_ACC != null || !EMAIL_ACC.trim().isEmpty()) {
            if(PASSWORD_ACC != null || !PASSWORD_ACC.trim().isEmpty()) {
        return userDao.loginValidate(EMAIL_ACC, PASSWORD_ACC);
            }
        }
        return false;
    }


    public boolean updateUserInfo(String id, String email, String phone, String role, String name) {
        // add check
        // chạy được
        return userDao.updateUser(id, email, phone, role, name);
    }

    public boolean changePassword(User_Entity user ,String newPassword) {
        // chạy được
        String email = user.getEMAIL_ACC();
        String oldPassword = user.getPASSWORD_ACC();
        if(login(email,oldPassword)){
            return userDao.changePasswordByEmail(email, newPassword);
        };
        return false;
    }

    // hàm dưới đây không cần tạo ở service mà cần phải tạo ở controller
    // , có thể tham kháo code dưới đây
    // hàm này chỉ để kiểm tra mail có tồn tại thì tạo token cho mail

//    public boolean forgotPassword(String email) {
//        if (userDao.isUserByMail(email)) {
//            CheckMailService check = new CheckMailService();
//            check.createToken(email);
//            return true;
//        }
//        return false;
//    }

}