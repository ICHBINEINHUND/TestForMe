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
//             nhét code vào đây
            ImportService importService = new ImportService(entityManager);
            Import_Detail_Entity importDetailEntity = new Import_Detail_Entity();
            importDetailEntity.setIS_AVAILABLE(null);
            List<Import_Detail_Entity> p = importService.getImportDetailByCombinedCondition(importDetailEntity,null, null, null, null, null, null, null);
            for (Import_Detail_Entity i : p) {
                System.out.println("import " + i.getID_IMPD());
            }
            System.out.println("size " + p.size());
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
