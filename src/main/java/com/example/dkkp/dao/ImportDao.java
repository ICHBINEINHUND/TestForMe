package com.example.dkkp.dao;

import com.example.dkkp.model.Email_Check_Entity;
import com.example.dkkp.model.Import_Entity;
import com.example.dkkp.model.Report_Bug;
import com.example.dkkp.model.Import_Entity;
import jakarta.persistence.*;

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

  public void createToken(Email_Check_Entity user) {
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

  public List<Import_Entity> getImportByDateImport(LocalDateTime dateJoin,String typeTimeCheck) {
    StringBuilder  jpql = new StringBuilder("SELECT u FROM Import_Entity u WHERE u.DATE_IMP ");
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

  private List<Import_Entity> getImportbyPrice(String price) {
    String jpql = "SELECT u FROM Import_Entity u WHERE u.PRICE_IMP = :price";
    TypedQuery<Import_Entity> query = entityManager.createQuery(jpql, Import_Entity.class);
    query.setParameter("price", price);
    return query.getResultList();
  }

  public List<Import_Entity> getImportByCombinedCondition(LocalDateTime dateJoin,String typeDate, String id, String price) {
    List<Import_Entity> result = null;

    List<List<Import_Entity>> conditions = List.of(
            dateJoin != null ? getImportByDateImport(dateJoin, typeDate) : null,
            id != null ? getImportByID(id) : null,
            price != null ? getImportbyPrice(price) : null
    );

    for (List<Import_Entity> condition : conditions) {
      if (condition != null) {
        if (result == null) {
          result = condition;
        } else {
          result.retainAll(condition);
        }
      }
    }
    return result != null ? result : List.of();
  }



  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}