package com.example.dkkp.dao;

import com.example.dkkp.model.Import_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class ImportDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public ImportDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  // Thêm phương thức getEntityManager
  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  public void createImport(Import_Entity importE) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(importE);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Import_Entity> getAllImport() {
    String jpql = "SELECT u FROM Import_Detail_Entity u";
    TypedQuery<Import_Entity> query = entityManager.createQuery(jpql, Import_Entity.class);
    return query.getResultList();
  }

  public List<Import_Entity> getFilteredImports(LocalDateTime dateJoin, String typeDate, String id, Boolean edited, String sortField, String sortOrder, int offset, int setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Import_Entity> query = cb.createQuery(Import_Entity.class);
    Root<Import_Entity> root = query.from(Import_Entity.class);

    Predicate conditions = cb.conjunction();

    if (dateJoin != null) {
      switch (typeDate) {
        case "<":
          conditions = cb.and(conditions, cb.lessThan(root.get("DATE_IMP"), dateJoin));
          break;
        case ">":
          conditions = cb.and(conditions, cb.greaterThan(root.get("DATE_IMP"), dateJoin));
          break;
        case "=":
          conditions = cb.and(conditions, cb.equal(root.get("DATE_IMP"), dateJoin));
          break;
      }
    }

    if (id != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_IMP"), id));
    }
    if (edited != null) {
      conditions = cb.and(conditions, cb.equal(root.get("EDITED"), edited));
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
    TypedQuery<Import_Entity> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult(offset); // Vị trí bắt đầu
    typedQuery.setMaxResults(setOff);  // Số lượng bản ghi mỗi lần

    return typedQuery.getResultList();
  }

  public void deleteImport(String id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Import_Entity importToDelete = entityManager.find(Import_Entity.class, id);
      if (importToDelete != null) {
        entityManager.remove(importToDelete);  // Xóa đối tượng
      }
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public boolean updateEditedImport(String id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();

      Import_Entity importToEdit = entityManager.find(Import_Entity.class, id);
      if (importToEdit == null) {
        return false;
      }
      importToEdit.setEDITED(true);
      entityManager.merge(importToEdit);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public boolean updateDescriptionImport(String id, String description) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();

      Import_Entity importToEdit = entityManager.find(Import_Entity.class, id);
      if (importToEdit == null) {
        return false;
      }
      importToEdit.setDESCRIPTION(description);
      entityManager.merge(importToEdit);
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
