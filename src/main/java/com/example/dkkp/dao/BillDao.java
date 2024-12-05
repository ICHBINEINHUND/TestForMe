package com.example.dkkp.dao;

import com.example.dkkp.model.Bill_Entity;
import com.example.dkkp.model.EnumType;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class BillDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public BillDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  public void createBill(Bill_Entity billE) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(billE);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Bill_Entity> getFilteredBills(LocalDateTime dateExport, String typeDate, String id, String phone, String add, String idUser, EnumType.Status_Bill Status, String sortField, String sortOrder, int offset, int setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Bill_Entity> query = cb.createQuery(Bill_Entity.class);
    Root<Bill_Entity> root = query.from(Bill_Entity.class);
    Predicate conditions = cb.conjunction();
    if (dateExport != null) {
      conditions = switch (typeDate) {
        case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_EXP"), dateExport));
        case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_EXP"), dateExport));
        case "=" -> cb.and(conditions, cb.equal(root.get("DATE_EXP"), dateExport));
        default -> conditions;
      };
    }
    if (id != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_BILL"), id));
    }
    if (idUser != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_USER"), idUser));
    }
    if (Status != null) {
      conditions = cb.and(conditions, cb.equal(root.get("BILL_STATUS"), Status));
    }
    if (phone != null) {
      conditions = cb.and(conditions, cb.equal(root.get("PHONE_BILL"), phone));
    }
    if (add != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_USER"), add));
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
    TypedQuery<Bill_Entity> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(setOff);
    return typedQuery.getResultList();
  }

  public boolean deleteBill(String id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Bill_Entity billToDelete = entityManager.find(Bill_Entity.class, id);
      if (billToDelete != null && billToDelete.getBILL_STATUS() == EnumType.Status_Bill.PEN) {
        billToDelete.setBILL_STATUS(EnumType.Status_Bill.CANC);
        entityManager.merge(billToDelete);
        transaction.commit();
        return true;
      }
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
    return false;
  }

  public void changeBillStatus(String id, EnumType.Status_Bill Status) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Bill_Entity billToChangeStatus = entityManager.find(Bill_Entity.class, id);
      if (billToChangeStatus != null) {
        billToChangeStatus.setBILL_STATUS(Status);
        entityManager.merge(billToChangeStatus);
        transaction.commit();
        return;
      }
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
        return;
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