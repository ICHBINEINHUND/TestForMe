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
            ProductFinalService productFinalService = new ProductFinalService(entityManager);
            Product_Option_Values_Entity optionValueEntity = new Product_Option_Values_Entity(null, null, null, 1);
            List<Product_Option_Values_Entity> p = productFinalService.getProductOptionValuesCombinedCondition(optionValueEntity, null, null, null, null);
            for (Product_Option_Values_Entity item : p) {
                System.out.println("ID " + item.getVALUE());
            }
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
