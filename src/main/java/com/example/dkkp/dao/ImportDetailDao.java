package com.example.dkkp.dao;

import com.example.dkkp.model.Import_Detail_Entity;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.sql.SQLException;
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

  public boolean createImportDetail(List<Import_Detail_Entity>  listImportDetail) throws SQLException {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      for (Import_Detail_Entity importDetail : listImportDetail) {
        System.out.println("service register1");
        entityManager.persist(importDetail);
        //add product
        System.out.println("service register2");
      }
      transaction.commit();
      return true;

    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
        System.out.println(e.getMessage() + "dcm loi ex");
      }
      return false;
    }
  }
  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  public List<Import_Detail_Entity> getFilteredImportDetails(String id,String idParent,String idSP, String sortField, String sortOrder, Integer offset, Integer setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Import_Detail_Entity> query = cb.createQuery(Import_Detail_Entity.class);
    Root<Import_Detail_Entity> root = query.from(Import_Detail_Entity.class);

    Predicate conditions = cb.conjunction();

    if (id != null && !id.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_IMPD"), id));
    }
    if (idParent != null && !idParent.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_IPARENT"), idParent));
    }
    if (idSP != null && !idSP.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_SP"), idSP));
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

    TypedQuery<Import_Detail_Entity> typedQuery = entityManager.createQuery(query);
    if(offset !=null) typedQuery.setFirstResult(offset);
    if(setOff !=null) typedQuery.setMaxResults(setOff);

    return typedQuery.getResultList();
  }



  public boolean deleteImportDetail(String idParent) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Query query = entityManager.createQuery(
              "UPDATE Import_Detail_Entity e SET e.AVAILABLE = false WHERE e.ID_IPARENT = :idParent"
      );
      query.setParameter("idParent", idParent);
      int rowsUpdated = query.executeUpdate();
      transaction.commit();
      return rowsUpdated > 0;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
        return false;
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
