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


public class TestController {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
//             nhét code vào đây
            BillService billService = new BillService(entityManager);
            Bill_Entity bill = new Bill_Entity();

            List<Bill_Entity> bills = billService.getBillByCombinedCondition(bill,null,null,null,null,null,null);
            for(Bill_Entity bill2 : bills) {
                System.out.println("bill " + bill2.getDATE_EXP().toString());
            }
            Integer number = billService.getCountBillByCombinedCondition(bill,null,null);
            System.out.println("so luong " + number);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
                System.out.println("da loi " + e.getMessage());
            }

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


}
