package com.example.dkkp.service;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.User_Entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class UserService {
  private final UserDao userDao;

  public UserService() {
    this.userDao = new UserDao();
  }

  public User_Entity getUsersByID(String id) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("ID Cannot Be Null!");
    }
    return userDao.getUsersByID(id);
  }

  public void registerNewUser(String ID_USER, String EMAIL_ACC, String PHONE_ACC, String PASSWORD_ACC, String ROLE_ACC, String NAME_USER) {
    LocalDateTime DATE_JOIN = LocalDateTime.now();
    User_Entity user = new User_Entity(ID_USER, EMAIL_ACC, PHONE_ACC, PASSWORD_ACC, ROLE_ACC, NAME_USER, DATE_JOIN);
    userDao.createUser(user);
    System.out.println("Pushed Successfully");
    getInformation(ID_USER);
  }

  public void getInformation(String ID_USER) {
    userDao.getUsersByID(ID_USER);
  }

  public boolean checkPass(String EMAIL_ACC, String PASSWORD) {
    User_Entity foundUsers = userDao.getUsersByMail(EMAIL_ACC);
    return Objects.equals(foundUsers.getPASSWORD_ACC(), PASSWORD);
  }

  public void login(String EMAIL_ACC, String PASSWORD_ACC) {
    userDao.loginValidate(EMAIL_ACC, PASSWORD_ACC);
    System.out.println("Pushed Successfully");
  }

  public boolean updateUserInfo(String id, String email, String phone, String password, String role, String name) {
    return userDao.updateUser(id, email, phone, password, role, name);
  }

  public boolean changePassword(String email, String oldPassword, String newPassword) {
    if (checkPass(email, oldPassword)) {
      return userDao.changePasswordByEmail(email, newPassword);
    }
    ;
    return false;
  }

  public boolean forgotPassword(String email) {
    if (userDao.isUserByMail(email)) {
      CheckMailService check = new CheckMailService();
      check.createToken(email);
      return true;
    }
    return false;
  }

  public boolean forgotPassword2(String email) {
    if (userDao.isUserByMail(email)) {
      CheckMailService check = new CheckMailService();
      check.createToken(email);
      Scanner ip = new Scanner(System.in);
      System.out.println("Nhap Token");
      String token = ip.nextLine();
      if (check.checkToken(email, token)) {
        System.out.println();
        String newPass = ip.nextLine();
        if (userDao.changePasswordByEmail(email, newPass)) {
          check.shutdown();
          UserDao.shutdown();
          return true;
        }
      }
      return false;
    }
    return false;
  }
}