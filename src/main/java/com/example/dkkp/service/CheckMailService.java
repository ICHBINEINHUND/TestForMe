package com.example.dkkp.service;

import com.example.dkkp.dao.EmailCheckDao;
import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.Email_Check_Entity;
import com.example.dkkp.model.User_Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class CheckMailService {
    private final EmailCheckDao checkmail;

    public CheckMailService() {
        this.checkmail = new EmailCheckDao();
    }

    public boolean createToken(String email) {
        try {
            checkmail.deleteTokensByEmail(email);
        }catch (Exception e){
        }
        Random random = new Random();
        LocalDateTime DATE_END = LocalDateTime.now().plusMinutes(30);
        int randomNumber = 100000 + random.nextInt(900000); // Tạo số từ 100000 đến 999999
        String token = String.valueOf(randomNumber);

        String id = "dfk";

        Email_Check_Entity check = new Email_Check_Entity(id, email, token, DATE_END);
        return checkmail.createToken(check);
    }

    public boolean checkToken(String mail, String token) {
//        if (token != null || !token.trim().isEmpty()) {
            System.out.println("dc");
            if(checkmail.checkToken(mail, token)) {
//                checkmail.deleteTokensByEmail(mail);
                return true;
            }
            return false;

//        }
//        return false;
    }
    public void shutdown(){
        EmailCheckDao.shutdown();
    }


}