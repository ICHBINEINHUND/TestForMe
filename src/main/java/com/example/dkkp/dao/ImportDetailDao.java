package com.example.dkkp.dao;

import com.example.dkkp.model.Import_Detail_Entity;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

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

  public List<Import_Detail_Entity> getFilteredImportDetails(String id, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity,String edited_id, String sortField, String sortOrder, int offset, int setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Import_Detail_Entity> query = cb.createQuery(Import_Detail_Entity.class);
    Root<Import_Detail_Entity> root = query.from(Import_Detail_Entity.class);

    Predicate conditions = cb.conjunction();

    if (id != null && !id.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_SP"), id));
    }

    if (edited_id != null && !edited_id.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("EDITED_ID"), edited_id));
    }

    if (minPrice != null) {
      conditions = cb.and(conditions, cb.greaterThanOrEqualTo(root.get("PRICE_IMP_SP"), minPrice));
    }
    if (maxPrice != null) {
      conditions = cb.and(conditions, cb.lessThanOrEqualTo(root.get("PRICE_IMP_SP"), maxPrice));
    }

    if (minQuantity != null) {
      conditions = cb.and(conditions, cb.greaterThanOrEqualTo(root.get("QUANTITY_SP"), minQuantity));
    }
    if (maxQuantity != null) {
      conditions = cb.and(conditions, cb.lessThanOrEqualTo(root.get("QUANTITY_SP"), maxQuantity));
    }

    query.where(conditions);

    if (sortField != null && sortOrder != null) {
      Path<?> sortPath = root.get(sortField);
      if ("desc".equalsIgnoreCase(sortOrder)) {
        query.orderBy(cb.desc(sortPath));
      } else {
        query.orderBy(cb.asc(sortPath));
      }
    }

    TypedQuery<Import_Detail_Entity> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(setOff);

    return typedQuery.getResultList();
  }



  public boolean deleteImportDetail(String id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Import_Detail_Entity import_detail = entityManager.find(Import_Detail_Entity.class, id);
      if (import_detail == null) {
        return false;
      }
      import_detail.setAVAILABLE(false);
      entityManager.merge(import_detail);
      transaction.commit();
      return true;
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
