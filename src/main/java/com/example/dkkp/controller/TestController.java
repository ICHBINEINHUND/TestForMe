package com.example.dkkp.controller;

import com.example.dkkp.model.*;
import com.example.dkkp.service.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

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
            Product_Final_Entity finalProduct = new Product_Final_Entity(null, 5,null,null,null,null, null, null);
            List<Product_Final_Entity> p = productFinalService.getProductFinalByCombinedCondition(finalProduct,null,null,null, "ID", "asc", null, null);
            for (Product_Final_Entity item : p) {
                System.out.println("ID " + item.getNAME_PRODUCT());
            }
//           Boolean b = reflectField.isPropertyNameMatched(Product_Base_Entity.class, sortField);
//            System.out.println(b);
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
