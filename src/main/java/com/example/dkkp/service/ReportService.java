package com.example.dkkp.service;

import com.example.dkkp.dao.ReportDao;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Report_Bug;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

public class ReportService {
    private final ReportDao reportDao;
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ReportService(EntityManager entityManager) {
        this.reportDao = new ReportDao(entityManager);
        this.entityManager = entityManager;
    }


    public boolean createNewReport(Report_Bug report) {
        // chạy được
        // add check kiểm tra các thuộc tính ở dưới
        // đây hợp lí thì mới cho return reportDao.createReport(rp);
        // còn không thì return false
        Integer ID_REPORT = report.getID_REPORT();
        EnumType.Bug_Type TYPE_BUG = report.getTYPE_BUG();
        String SCRIPT_BUG = report.getSCRIPT_BUG();
        report.setDATE_REPORT(LocalDateTime.now());
        String ID_USER = report.getID_USER();

        return reportDao.createReport(report);

    }

    public boolean deleteReport(Integer id) {
        // chạy được
            if (id == null) {
                return reportDao.deleteAllReports();
            }
            reportDao.deleteReportById(id);
            return true;
    }

    public List<Report_Bug> getFilteredReports(
            Report_Bug report,
            String typeDate,
            String sortField,
            String sortOrder
    ) {
        // chạy được
        // không cần kiểm tra sự hợp lệ của các tham số truyền vào khác như userId,...
        if (  sortField == null || reflectField.isPropertyNameMatched(Report_Bug.class, sortField) ) {
            String userId = report.getID_USER();
            Integer reportId = report.getID_REPORT();
            LocalDateTime dateReport = report.getDATE_REPORT();
            EnumType.Bug_Type bugType = report.getTYPE_BUG();
            return reportDao.getFilteredReports(userId, reportId, bugType, dateReport, typeDate, sortField, sortOrder);
        }
        return null;
    }


}