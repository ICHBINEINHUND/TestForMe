package com.example.dkkp.service;

import com.example.dkkp.dao.ReportDao;
import com.example.dkkp.model.Report_Bug;

import static com.example.dkkp.model.EnumType.Bug_Type.UI;

import java.time.LocalDateTime;
import java.util.List;

public class ReportService {
  private final ReportDao reportDao;

  public ReportService() {
    this.reportDao = new ReportDao();
  }

  public void createNewReport() {
    String ID_REPORT = " 12ee22";
    String SCRIPT_BUG = "098748950";
    LocalDateTime DATE_REPORT = LocalDateTime.now();
    String ID_USER = "12ee2";
    Report_Bug rp = new Report_Bug(ID_REPORT, UI, SCRIPT_BUG, DATE_REPORT, ID_USER);
    reportDao.createReport(rp);
    System.out.println("Pushed Successfully");
  }

  public void deleteReport(String id) {
    if (id == null) {
      reportDao.deleteAllReports();
    } else {
      reportDao.deleteReportById(id);
    }
  }

  public List<Report_Bug> getFilteredReports(String userId, String reportId, LocalDateTime dateReport, String typeDate, String sortField, String sortOrder, int offset, int setOff) {
    return reportDao.getFilteredReports(userId, reportId, dateReport, typeDate, sortField, sortOrder, offset, setOff);
  }
}