package com.example.dkkp.controller;

import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Report_Bug;
import com.example.dkkp.service.ReportService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class TestController {

//        "bkl1@gmail.com","pass123"
//        "ca677198d7ebbc24736d79bdc4a493b0293f363d5e881985d190e2c626376dff"
public static void main(String[] args) throws Exception {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
        transaction.begin();
        ReportService reportService = new ReportService(entityManager);
        Report_Bug report_bug = new Report_Bug(1, EnumType.Bug_Type.NET, "Khong ket noi duoc", null, "ca677198d7ebbc24736d79bdc4a493b0293f363d5e881985d190e2c626376dff");
        reportService.createNewReport(report_bug);
        transaction.commit();
    } catch (Exception e) {
        if (transaction.isActive()) {
            transaction.rollback();
        }
    } finally {
        entityManager.close();
        entityManagerFactory.close();
    }
}

}
