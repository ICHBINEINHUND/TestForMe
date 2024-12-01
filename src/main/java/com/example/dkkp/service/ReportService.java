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


  public void createNewReport(){
    String ID_REPORT =" 12ee22";
    EnumType.Bug_Type TYPE_BUG =UI;
    String SCRIPT_BUG = "098748950";
    LocalDateTime DATE_REPORT = LocalDateTime.now();
    String ID_USER = "12ee2";

    Report_Bug rp = new Report_Bug(ID_REPORT,TYPE_BUG,SCRIPT_BUG,DATE_REPORT,ID_USER);
    reportDao.createReport(rp);
    System.out.println("da push thanh cong");
  }

  public void deleteReport(String id){
    if(id == null){
      reportDao.deleteAllReports();
    }else{
      reportDao.deleteReportById(id);
    }
  }

  public List<Report_Bug> getReportByCombinedCondition(LocalDateTime dateJoin, String typeDate, String userId,String id, String sortField, String sortOrder) {
    List<Report_Bug> result;

    if (dateJoin == null && id == null) {
      result = reportDao.getAllImport();
    } else {
      result = null;
      List<List<Report_Bug>> conditions = List.of(
              dateJoin != null ? reportDao.getReport_BugByDate(dateJoin, typeDate) : null,
              id != null ? reportDao.getReport_BugByReportID(id) : null,
              userId != null ? reportDao.getReport_BugByReportID(userId) : null
      );

      for (List<Report_Bug> condition : conditions) {
        if (condition != null) {
          if (result == null) {
            result = condition;
          } else {
            result.retainAll(condition);
          }
        }
      }
    }

    if (result != null && sortField != null && sortOrder != null) {
      result = reportDao.sortResults(result, sortField, sortOrder);
    }
    return result != null ? result : List.of();
  }
}