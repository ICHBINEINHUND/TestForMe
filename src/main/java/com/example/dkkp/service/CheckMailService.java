package com.example.dkkp.service;

import com.example.dkkp.dao.EmailCheckDao;
import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.Email_Check_Entity;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class CheckMailService {
  private final EmailCheckDao checkmail;
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public CheckMailService() {
    this.checkmail = new EmailCheckDao();
    this.entityManager = entityManagerFactory.createEntityManager();
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