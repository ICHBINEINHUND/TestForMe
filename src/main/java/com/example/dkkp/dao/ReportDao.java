package com.example.dkkp.dao;

import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Report_Bug;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public ReportDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  // Tạo báo cáo
  public boolean createReport(Report_Bug report) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(report);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Report_Bug> getFilteredReports(
          String userId,
          String reportId,
          EnumType.Bug_Type status,
          LocalDateTime dateReport,
          String typeDate,
          String sortField,
          String sortOrder
  ) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Report_Bug> query = cb.createQuery(Report_Bug.class);
    Root<Report_Bug> root = query.from(Report_Bug.class);

    Predicate conditions = cb.conjunction();

    if (userId != null && !userId.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_USER"), userId));
    }

    if (reportId != null && !reportId.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_REPORT"), reportId));
    }
    if (status != null) {
      conditions = cb.and(conditions, cb.equal(root.get("TYPE_BUG"), status));
    }

    if (dateReport != null && typeDate != null) {
      switch (typeDate) {
        case "<":
          conditions = cb.and(conditions, cb.lessThan(root.get("DATE_REPORT"), dateReport));
          break;
        case "<=":
          conditions = cb.and(conditions, cb.lessThanOrEqualTo(root.get("DATE_REPORT"), dateReport));
          break;
        case "=":
          conditions = cb.and(conditions, cb.equal(root.get("DATE_REPORT"), dateReport));
          break;
        case ">=":
          conditions = cb.and(conditions, cb.greaterThanOrEqualTo(root.get("DATE_REPORT"), dateReport));
          break;
        case ">":
          conditions = cb.and(conditions, cb.greaterThan(root.get("DATE_REPORT"), dateReport));
          break;
        default:
          throw new IllegalArgumentException("Invalid typeDate: " + typeDate);
      }
    }

    query.where(conditions);


    if (sortField != null && sortOrder != null) {
      Path<?> sortPath = root.get(sortField.toUpperCase());
      if ("desc".equalsIgnoreCase(sortOrder)) {
        query.orderBy(cb.desc(sortPath));
      } else {
        query.orderBy(cb.asc(sortPath));
      }
    }


    TypedQuery<Report_Bug> typedQuery = entityManager.createQuery(query);
    return typedQuery.getResultList();
  }


  public boolean deleteReportById(String reportId) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Report_Bug reportToDelete = entityManager.find(Report_Bug.class, reportId);
      if (reportToDelete == null) {
        return false;
      }
      entityManager.remove(reportToDelete);
      transaction.commit();
      return true;
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
      transaction.commit();
      return deletedCount > 0;
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
