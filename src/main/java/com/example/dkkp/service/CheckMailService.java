package com.example.dkkp.service;

import com.example.dkkp.dao.EmailCheckDao;
import com.example.dkkp.model.Email_Check_Entity;

import java.time.LocalDateTime;
import java.util.Random;

public class CheckMailService {
  private final EmailCheckDao checkmail;

  public CheckMailService() {
    this.checkmail = new EmailCheckDao();
  }

  public void createToken(String email) {
    checkmail.deleteTokensByEmail(email);
    Random random = new Random();
    LocalDateTime DATE_END = LocalDateTime.now().plusMinutes(30);
    int randomNumber = 100000 + random.nextInt(900000);
    String token = String.valueOf(randomNumber);
    String id = "dfk";
    Email_Check_Entity check = new Email_Check_Entity(id, email, token, DATE_END);
    checkmail.createToken(check);
  }

  public boolean checkToken(String mail, String token) {
    if (token != null) {
      System.out.println("dc");
      if (checkmail.checkToken(mail, token)) {
        checkmail.deleteTokensByEmail(mail);
        return true;
      }
      return false;

    }
    return false;
  }

  public void shutdown() {
    EmailCheckDao.shutdown();
  }
}