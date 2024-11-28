package com.example.dkkp.service;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.User_Entity;

import java.util.List;

public class UserService {
  private final UserDao userDao;

  public UserService() {
    this.userDao = new UserDao();
  }

  public List<User_Entity> getUsersByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name Cannot Be Null!");
    }
    return userDao.getUsersByName(name);
  }
}