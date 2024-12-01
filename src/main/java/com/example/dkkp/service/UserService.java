package com.example.dkkp.service;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.User_Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class UserService {
  private final UserDao userDao;

  public UserService() {
    this.userDao = new UserDao();
  }

  public List<User_Entity> getUsersByName(String id) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("ID Cannot Be Null!");
    }
    return userDao.getUsersByID(id);
  }

  public void createNewUser(){
    String ID_USER ="12ee2";
    String EMAIL_ACC ="22s223e@gmail.com";
    String PHONE_ACC = "098748950";
    String PASSWORD_ACC = "jdlalk1";
    String ROLE_ACC = "admin";
    String NAME_USER = "memay beo";
    LocalDateTime DATE_JOIN = LocalDateTime.now();
//    Timestamp DATE_JOIN = Timestamp.valueOf(time);

//    if (ID_USER == null && EMAIL_ACC == null && PHONE_ACC == null && PASSWORD_ACC == null && ROLE_ACC == null && NAME_USER == null ) {
//      return;
//    }
    User_Entity user = new User_Entity(ID_USER,EMAIL_ACC,PHONE_ACC,PASSWORD_ACC,ROLE_ACC,NAME_USER,DATE_JOIN);
    userDao.createUser(user);
    System.out.println("da push thanh cong");
  }
}