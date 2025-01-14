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
//       ProductFinalService productFinalService = new ProductFinalService(entityManager);
//       Product_Option_Entity productOption_Entity = new Product_Option_Entity();
//     List<Product_Option_Entity> p =  productFinalService.getProductOptionCombinedCondition(productOption_Entity,null,null,null,null);
//               for(Product_Option_Entity item: p){
//                   System.out.println("San pham: " + item.getNAME_OPTION());
//               }
            ProductBaseService productBaseService = new ProductBaseService(entityManager);
            productBaseService.deleteProductAttributeValues(6,null,null);
//                   for(Product_Attribute_Values_Entity item:p){
//                       System.out.println("day la " + item.getVALUE() + " " + item.getNAME_PRODUCT() );
//                   }

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
