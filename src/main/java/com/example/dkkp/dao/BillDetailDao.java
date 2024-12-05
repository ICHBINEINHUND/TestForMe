package com.example.dkkp.dao;

import com.example.dkkp.model.Bill_Detail_Entity;
import com.example.dkkp.model.Bill_Entity;
import com.example.dkkp.model.EnumType;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class BillDetailDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public BillDetailDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createBillDetail(List<Bill_Detail_Entity> listBillDetail) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      int batchSize = 10;
      for (int i = 0; i < listBillDetail.size(); i++) {
        Bill_Detail_Entity billDetail = listBillDetail.get(i);
        entityManager.persist(billDetail);

        if (i % batchSize == 0 && i > 0) {
          entityManager.flush();
          entityManager.clear();
        }
      }
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Bill_Detail_Entity> getFilteredImportDetails(String id, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity, String edited_id, String sortField, String sortOrder, int offset, int setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Bill_Detail_Entity> query = cb.createQuery(Bill_Detail_Entity.class);
    Root<Bill_Detail_Entity> root = query.from(Bill_Detail_Entity.class);
    Predicate conditions = cb.conjunction();
    if (id != null && !id.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_BILL"), id));
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
      conditions = cb.and(conditions, cb.greaterThanOrEqualTo(root.get("QUANTITY_BILL"), minQuantity));
    }
    if (maxQuantity != null) {
      conditions = cb.and(conditions, cb.lessThanOrEqualTo(root.get("QUANTITY_BILL"), maxQuantity));
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
    TypedQuery<Bill_Detail_Entity> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(setOff);
    return typedQuery.getResultList();
  }

  public boolean cancelBillDetail(String id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Bill_Entity bill = entityManager.find(Bill_Entity.class, id);
      if (bill == null) {
        throw new RuntimeException("Bill Not Found");
      }
      if (bill.getBILL_STATUS() == EnumType.Status_Bill.SHIP || bill.getBILL_STATUS() == EnumType.Status_Bill.CONF || bill.getBILL_STATUS() == EnumType.Status_Bill.DELI) {
        return false;
      }
      List<Bill_Detail_Entity> billDetails = entityManager.createQuery("SELECT bd FROM Bill_Detail_Entity bd WHERE bd.ID_PARENT = :id", Bill_Detail_Entity.class).setParameter("id", id).getResultList();
      if (billDetails.isEmpty()) {
        throw new RuntimeException("No Bill Details Found For The Given ID_PARENT");
      }
      for (Bill_Detail_Entity billDetail : billDetails) {
        billDetail.setAVAILABLE(false);
        try {
          entityManager.merge(billDetail);
        } catch (PersistenceException e) {
          throw new RuntimeException("Failed To Update Bill Detail With ID_PARENT: " + id, e);
        }
      }
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