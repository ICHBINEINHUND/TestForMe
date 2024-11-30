package com.example.dkkp.dao;

import com.example.dkkp.model.Report_Bug;
import jakarta.persistence.*;

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

  public void createUser(Report_Bug user) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(user);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Report_Bug> getAllUsers() {
    String jpql = "SELECT u FROM Report_Bug u";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    return query.getResultList();
  }

  public List<Report_Bug> getUsersByID(String id) {
    String jpql = "SELECT u FROM Report_Bug u WHERE u.ID_USER = :id";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    query.setParameter("id", id);
    return query.getResultList();
  }

  public List<Report_Bug> getUsersByDateReportBefore(LocalDateTime dateJoin) {
    String jpql = "SELECT u FROM Report_Bug u WHERE u.DATE_REPORT < :dateJoin";
    TypedQuery<Report_Bug> query = entityManager.createQuery(jpql, Report_Bug.class);
    query.setParameter("dateJoin", dateJoin);
    return query.getResultList();
  }

  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}