package com.example.dkkp.service;

import com.example.dkkp.dao.ReportDao ;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Report_Bug;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.dkkp.model.EnumType.Bug_Type.UI;

public class ReportService {
  private final ReportDao reportDao ;

  public ReportService() {
    this.reportDao  = new ReportDao ();
  }

  public List<Report_Bug> getUsersByName(String id) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("ID Cannot Be Null!");
    }
    return reportDao.getReportsByID(id);
  }

  public void createNewReport(){
    String ID_REPORT =" 12ee22";
    EnumType.Bug_Type TYPE_BUG =UI;
    String SCRIPT_BUG = "098748950";
    LocalDateTime DATE_REPORT = LocalDateTime.now();
    String ID_USER = "12ee2";

    Report_Bug rp = new Report_Bug(ID_REPORT,TYPE_BUG,SCRIPT_BUG,DATE_REPORT,ID_USER);
    reportDao .createReport(rp);
    System.out.println("da push thanh cong");
  }
}