package com.example.dkkp.controller;

import com.example.dkkp.model.*;
import com.example.dkkp.service.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.dkkp.controller.LoginController.entityManager;

public class TestController {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            transaction.begin();
            Path currentDir = Path.of(System.getProperty("user.dir"));
            System.out.println(currentDir + "fiel");
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


}
