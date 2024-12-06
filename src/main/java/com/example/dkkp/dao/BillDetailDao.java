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

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void createBillDetail(List<Bill_Detail_Entity> listBillDetail) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Integer batchSize = 10;
            for (Integer i = 0; i < listBillDetail.size(); i++) {
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

    public List<Bill_Detail_Entity> getFilteredBillDetails(String id, Double Price,String idSP, Integer Quantity, String idParent,Boolean availabe, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill_Detail_Entity> query = cb.createQuery(Bill_Detail_Entity.class);
        Root<Bill_Detail_Entity> root = query.from(Bill_Detail_Entity.class);

        Predicate conditions = cb.conjunction();

        if (id != null && !id.trim().isEmpty()) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BILL_DETAIL"), id));
        }

        if (Price != null) {
            conditions = cb.and(conditions, cb.greaterThanOrEqualTo(root.get("PRICE_BUY"), Price));
        }
        if (Quantity != null) {
            conditions = cb.and(conditions, cb.equal(root.get("QUANTITY_BILL"), Quantity));
        }

        if (idParent != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_PARENT"), idParent));
        }
        if (idSP != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_SP"), idSP));
        }
        if (availabe != null) {
            conditions = cb.and(conditions, cb.equal(root.get("AVAILABLE"), availabe));
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

        TypedQuery<Bill_Detail_Entity> typedQuery = entityManager.createQuery(query);
        if(offset !=null) typedQuery.setFirstResult(offset);
        if(setOff !=null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }


    public boolean cancelBillDetail(String id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Bill_Entity bill = entityManager.find(Bill_Entity.class, id);
            if (bill == null) {
                throw new RuntimeException("Bill not found");
            }

            if (bill.getBILL_STATUS() == EnumType.Status_Bill.SHIP
                    || bill.getBILL_STATUS() == EnumType.Status_Bill.CONF
                    || bill.getBILL_STATUS() == EnumType.Status_Bill.DELI
            ) {
                return false;
            }
            List<Bill_Detail_Entity> billDetails = entityManager.createQuery(
                            "SELECT bd FROM Bill_Detail_Entity bd WHERE bd.ID_PARENT = :id", Bill_Detail_Entity.class)
                    .setParameter("id", id)
                    .getResultList();

            if (billDetails.isEmpty()) {
                throw new RuntimeException("No Bill Details found for the given ID_PARENT");
            }

            for (Bill_Detail_Entity billDetail : billDetails) {
                billDetail.setAVAILABLE(false);
                try {
                    entityManager.merge(billDetail);
                } catch (PersistenceException e) {
                    throw new RuntimeException("Failed to update Bill Detail with ID_PARENT: " + id, e);
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
