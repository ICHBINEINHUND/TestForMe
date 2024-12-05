package com.example.dkkp;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.User_Entity;
import com.example.dkkp.service.CheckMailService;
import com.example.dkkp.service.ReportService;
import com.example.dkkp.service.UserService;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import com.example.dkkp.view.LoginView;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("dc");
//
//        UserService userService = new UserService();
//        userService.forgotPassword("22s223e@gmail.com");


//        UserService userService = new UserService();
//        String ID_USER = "12ee2";
//        String EMAIL_ACC = "22s223e@gmail.com";
//        String PHONE_ACC = "098748950";
//        String PASSWORD_ACC = "jdlalk1";
//        String ROLE_ACC = "admin";
//        String NAME_USER = "memay beo";
//        userService.registerNewUser(ID_USER, EMAIL_ACC, PHONE_ACC, PASSWORD_ACC, ROLE_ACC, NAME_USER);
//        List<User_Entity> foundUsers = userService.getUsersByID(ID_USER);
//        if (foundUsers.isEmpty()) {
//            System.out.println("No Users Found With The Name: " + ID_USER);
//        } else {
//            for (User_Entity foundUser : foundUsers) {
//                System.out.println("ID: " + foundUser.getID_USER() + ", Name: " + foundUser.getNAME_USER() + ", Date Joined: " + foundUser.getDATE_JOIN());
//            }
//        }

        CheckMailService checkM = new CheckMailService();

//        if(checkM.createToken("efn@gmail.com")){
//            System.out.println("da them token thanh cong");
//        }else {
//            System.out.println("them that bai");
//        };

        if(checkM.checkToken("22s223e@gmail.com","934187")){
            System.out.println("thanh cong");
        }else {
            System.out.println("khong thanh cong");
        };
    }
  public static void main(String[] args) {
    launch(args);
  }
}