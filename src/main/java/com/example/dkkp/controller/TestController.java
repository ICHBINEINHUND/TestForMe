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

public class TestController {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

//            Product_Base_Entity pd = new Product_Base_Entity(null,"Pd 3",20,LocalDateTime.now(),"des pd",20,1,5);
            Product_Base_Entity pd = new Product_Base_Entity(null,null,null,null,"des pd",null,null,null,"Apple",null);
            ProductBaseService productBaseService = new ProductBaseService(entityManager);
//            productBaseService.createProductBase(pd);
            List<Product_Base_Entity> o = productBaseService.getProductBaseByCombinedCondition(pd,null,null,null,null,null,null,null);
            for(Product_Base_Entity p : o){
                System.out.println(p.getID_BASE_PRODUCT());
                System.out.println(p.getNAME_PRODUCT());
                System.out.println(p.getID_CATEGORY());
                System.out.println(p.getNAME_CATEGORY());
                System.out.println("-----");
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
