package com.example.dkkp.dao;

import com.example.dkkp.model.Import_Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImportDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public ImportDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
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

  public List<Import_Entity> sortById(List<Import_Entity> imports, String sortOrder) {
    return imports.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Import_Entity::getID_IMP).reversed()
                    : Comparator.comparing(Import_Entity::getID_IMP))
            .collect(Collectors.toList());
  }

  public List<Import_Entity> sortByDate(List<Import_Entity> imports, String sortOrder) {
    return imports.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Import_Entity::getDATE_IMP).reversed()
                    : Comparator.comparing(Import_Entity::getDATE_IMP))
            .collect(Collectors.toList());
  }

  public List<Import_Entity> getImportByDateImport(LocalDateTime dateJoin, String typeTimeCheck) {
    StringBuilder jpql = new StringBuilder("SELECT u FROM Import_Entity u WHERE u.DATE_IMP ");
    if ("<".equals(typeTimeCheck)) {
      jpql.append("< :dateJoin");
    } else if (">".equals(typeTimeCheck)) {
      jpql.append("> :dateJoin");
    } else if ("=".equals(typeTimeCheck)) {
      jpql.append("= :dateJoin");
    }
    TypedQuery<Import_Entity> query = entityManager.createQuery(jpql.toString(), Import_Entity.class);
    query.setParameter("dateJoin", dateJoin);
    return query.getResultList();
  }

  public List<Import_Entity> getImportByID(String id) {
    String jpql = "SELECT u FROM Import_Entity u WHERE u.ID_IMP = :id";
    TypedQuery<Import_Entity> query = entityManager.createQuery(jpql, Import_Entity.class);
    query.setParameter("id", id);
    return query.getResultList();
  }

  // Phương thức sắp xếp chung cho tất cả các trường
  public List<Import_Entity> sortResults(List<Import_Entity> imports, String sortField, String sortOrder) {
    switch (sortField) {
      case "id":
        return sortById(imports, sortOrder);
      case "date":
        return sortByDate(imports, sortOrder);
      default:
        throw new IllegalArgumentException("Invalid sort field: " + sortField);
    }
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

  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}
