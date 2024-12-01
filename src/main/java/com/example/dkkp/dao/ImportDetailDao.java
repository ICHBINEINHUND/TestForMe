package com.example.dkkp.dao;

import com.example.dkkp.model.Email_Check_Entity;
import com.example.dkkp.model.Import_Detail_Entity;
import com.example.dkkp.model.Import_Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ImportDetailDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public ImportDetailDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createImportDetail(Import_Detail_Entity importDetail) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(importDetail);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Import_Detail_Entity> getAllImportDetails() {
    String jpql = "SELECT u FROM Import_Detail_Entity u";
    TypedQuery<Import_Detail_Entity> query = entityManager.createQuery(jpql, Import_Detail_Entity.class);
    return query.getResultList();
  }

  public List<Import_Detail_Entity> getImportDetailByID(String id) {
    String jpql = "SELECT u FROM Import_Entity u WHERE u.ID_IMP = :id";
    TypedQuery<Import_Detail_Entity> query = entityManager.createQuery(jpql, Import_Detail_Entity.class);
    query.setParameter("id", id);
    return query.getResultList();
  }

  private List<Import_Detail_Entity> getImportDetailbyPrice(String price) {
    String jpql = "SELECT u FROM Import_Detail_Entity u WHERE u.PRICE_IMP_SP = :price";
    TypedQuery<Import_Detail_Entity> query = entityManager.createQuery(jpql, Import_Detail_Entity.class);
    query.setParameter("price", price);
    return query.getResultList();
  }


  public List<Import_Detail_Entity> sortByPrice() {
    return getAllImportDetails()
            .stream()
            .sorted((e1, e2) -> Double.compare(e1.getPRICE_IMP_SP(), e2.getPRICE_IMP_SP()))
            .collect(Collectors.toList());
  }

  public List<Import_Detail_Entity> sortByQuantity() {
    return getAllImportDetails()
            .stream()
            .sorted((e1, e2) -> Integer.compare(e1.getQUANTITY_SP(), e2.getQUANTITY_SP()))
            .collect(Collectors.toList());
  }

  public List<Import_Detail_Entity> sortById() {
    return getAllImportDetails()
            .stream()
            .sorted((e1, e2) -> e1.getID_SP().compareTo(e2.getID_SP()))
            .collect(Collectors.toList());
  }




  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}