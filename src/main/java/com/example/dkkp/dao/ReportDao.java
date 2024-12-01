package com.example.dkkp.dao;

import com.example.dkkp.model.Import_Entity;
import com.example.dkkp.model.Report_Bug;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public ReportDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createReport(Report_Bug report) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(report);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Report_Bug> getAllImport() {
    String jpql = "SELECT u FROM Report_Bug u";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    return query.getResultList();
  }

  public List<Report_Bug> sortById(List<Report_Bug> report, String sortOrder) {
    return report.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Report_Bug::getID_USER).reversed()
                    : Comparator.comparing(Report_Bug::getID_USER))
            .collect(Collectors.toList());
  }
  public List<Report_Bug> sortByUserId(List<Report_Bug> report, String sortOrder) {
    return report.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Report_Bug::getID_USER).reversed()
                    : Comparator.comparing(Report_Bug::getID_USER))
            .collect(Collectors.toList());
  }

  public List<Report_Bug> sortByDate(List<Report_Bug> report, String sortOrder) {
    return report.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Report_Bug::getDATE_REPORT).reversed()
                    : Comparator.comparing(Report_Bug::getDATE_REPORT))
            .collect(Collectors.toList());
  }

  public List<Report_Bug> getReport_BugByDate(LocalDateTime dateJoin, String typeTimeCheck) {
    StringBuilder jpql = new StringBuilder("SELECT u FROM Report_Bug u WHERE u.DATE_REPORT ");
    if ("<".equals(typeTimeCheck)) {
      jpql.append("< :dateJoin");
    } else if (">".equals(typeTimeCheck)) {
      jpql.append("> :dateJoin");
    } else if ("=".equals(typeTimeCheck)) {
      jpql.append("= :dateJoin");
    }
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql.toString(), Report_Bug.class);
    query.setParameter("dateJoin", dateJoin);
    return query.getResultList();
  }

  public List<Report_Bug> getReport_BugByUserID(String id) {
    String jpql = "SELECT u FROM Report_Bug u WHERE u.ID_USER = :id";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    query.setParameter("id", id);
    return query.getResultList();
  }

  public List<Report_Bug> getReport_BugByReportID(String id) {
    String jpql = "SELECT u FROM Report_Bug u WHERE u.ID_REPORT = :id";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    query.setParameter("id", id);
    return query.getResultList();
  }

  public List<Report_Bug> getReportsByDateReportBefore(LocalDateTime dateJoin) {
    String jpql = "SELECT u FROM Report_Bug u WHERE u.DATE_REPORT < :dateJoin";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    query.setParameter("dateJoin", dateJoin);
    return query.getResultList();
  }

  public List<Report_Bug> sortResults(List<Report_Bug> report, String sortField, String sortOrder) {
    switch (sortField) {
      case "id":
        return sortById(report, sortOrder);
      case "date":
        return sortByDate(report, sortOrder);
      case "userId":
        return sortByUserId(report, sortOrder);
      default:
        throw new IllegalArgumentException("Invalid sort field: " + sortField);
    }
  }

  public boolean deleteReportById(String reportId) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Report_Bug reportToDelete = entityManager.find(Report_Bug.class, reportId);
      if (reportToDelete != null) {
        entityManager.remove(reportToDelete);
        transaction.commit();
        return true;
      }
      transaction.commit();
      return false;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public boolean deleteAllReports() {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      String jpql = "DELETE FROM Report_Bug";
      int deletedCount = entityManager.createQuery(jpql).executeUpdate();
      if (deletedCount > 0) {
        transaction.commit();
        return true;
      }
      transaction.commit();
      return false;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}