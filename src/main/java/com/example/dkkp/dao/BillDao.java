package com.example.dkkp.dao;

import com.example.dkkp.model.Bill_Entity;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Import_Entity;
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

    // Thêm phương thức getEntityManager
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public boolean createBill(Bill_Entity billE) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(billE);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Bill_Entity getBillByID(String id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Bill_Entity importToCheck = entityManager.find(Bill_Entity.class, id);
            transaction.commit();
            return importToCheck;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return null;
            }
            return null;
        }
    }

    public List<Bill_Entity> getFilteredBills(LocalDateTime dateExport, String typeDate, String id, String phone, String add, String idUser, EnumType.Status_Bill Status, String addBill, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill_Entity> query = cb.createQuery(Bill_Entity.class);
        Root<Bill_Entity> root = query.from(Bill_Entity.class);

        Predicate conditions = cb.conjunction();

        if (dateExport != null) {
            switch (typeDate) {
                case "<":
                    conditions = cb.and(conditions, cb.lessThan(root.get("DATE_EXP"), dateExport));
                    break;
                case ">":
                    conditions = cb.and(conditions, cb.greaterThan(root.get("DATE_EXP"), dateExport));
                    break;
                case "=":
                    conditions = cb.and(conditions, cb.equal(root.get("DATE_EXP"), dateExport));
                    break;
            }
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
            conditions = cb.and(conditions, cb.equal(root.get("ADD_BILL"), add));
        }


        query.where(conditions);
        if (sortField != "PHONE_BILL" && sortField != "ADD_BILL") {
            if (sortField != null && sortOrder != null) {
                Path<?> sortPath = root.get(sortField.toUpperCase());
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    query.orderBy(cb.desc(sortPath));
                } else {
                    query.orderBy(cb.asc(sortPath));
                }
            }
        }

        TypedQuery<Bill_Entity> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(offset); // Vị trí bắt đầu
        typedQuery.setMaxResults(setOff);  // Số lượng bản ghi mỗi lần

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

    public boolean changeBillStatus(String id, EnumType.Status_Bill Status) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Bill_Entity billToChangeStatus = entityManager.find(Bill_Entity.class, id);
            if (billToChangeStatus != null) {
                billToChangeStatus.setBILL_STATUS(Status);
                entityManager.merge(billToChangeStatus);
                transaction.commit();
                return true;
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return false;
            }
            throw e;
        }
        return false;
    }


    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
