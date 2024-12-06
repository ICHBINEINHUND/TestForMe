package com.example.dkkp.service;

import com.example.dkkp.dao.ReportDao;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Report_Bug;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.dkkp.model.EnumType.Bug_Type.UI;

public class ReportService {
    private final ReportDao reportDao;

    public ReportService() {
        this.reportDao = new ReportDao();
    }


    public boolean createNewReport(Report_Bug report) {
        // chạy được
        // add check kiểm tra các thuộc tính ở dưới
        // đây hợp lí thì mới cho return reportDao.createReport(rp);
        // còn không thì return false
        try {
            String ID_REPORT = report.getID_REPORT();
            EnumType.Bug_Type TYPE_BUG = report.getTYPE_BUG();
            String SCRIPT_BUG = report.getSCRIPT_BUG();
            LocalDateTime DATE_REPORT = report.getDATE_REPORT();
            String ID_USER = report.getID_USER();

            Report_Bug rp = new Report_Bug(ID_REPORT, TYPE_BUG, SCRIPT_BUG, DATE_REPORT, ID_USER);
            return reportDao.createReport(rp);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public boolean deleteReport(String id) {
        // chạy được
        try {
            if (id == null) {
                return reportDao.deleteAllReports();
            } else {
                return reportDao.deleteReportById(id);

            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<Report_Bug> getFilteredReports(
            Report_Bug report,
            String typeDate,
            String sortField,
            String sortOrder
    ) {
        // chạy được
        // không cần kiểm tra sự hợp lệ của các tham số truyền vào khác như userId,...
        try {
            if (reflectField.isPropertyNameMatched(report, sortField) || sortField == null) {
                String userId = report.getID_USER();
                String reportId = report.getID_REPORT();
                LocalDateTime dateReport = report.getDATE_REPORT();
                EnumType.Bug_Type bugType = report.getTYPE_BUG();
                return reportDao.getFilteredReports(userId, reportId, bugType, dateReport, typeDate, sortField, sortOrder);
            }
            return null;
        } catch (
                RuntimeException e) {
            throw e;
        }
    }


}