package com.example.dkkp;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.User_Entity;
import com.example.dkkp.service.UserService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    User_Entity user = new User_Entity("NAZI", "Adolf Hitler", Date.valueOf("2024-11-28"));
    UserDao userDao = new UserDao();
    userDao.createUser(user);
    System.out.println("User Has Been Added Successfully!");

    List<User_Entity> allUsers = userDao.getAllUsers();
    for (User_Entity u : allUsers) {
      System.out.println("ID: " + u.getID_USER() + ", Name: " + u.getNAME_USER() + ", Date Joined: " + u.getDATE_JOIN());
    }

    UserService userService = new UserService();
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.print("Enter Username To Search: ");
      String userName = scanner.nextLine();
      List<User_Entity> foundUsers = userService.getUsersByName(userName);
      if (foundUsers.isEmpty()) {
        System.out.println("No Users Found With The Name: " + userName);
      } else {
        for (User_Entity foundUser : foundUsers) {
          System.out.println("ID: " + foundUser.getID_USER() + ", Name: " + foundUser.getNAME_USER() + ", Date Joined: " + foundUser.getDATE_JOIN());
        }
      }
    }
  }
}