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

    public BillDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Thêm phương thức getEntityManager
    public EntityManager getEntityManager() {
        return this.entityManager;
    }


    public void createBill(Bill_Entity billE) {
            entityManager.persist(billE);
    }

    public Bill_Entity getBillByID(Integer idBill) {
        return entityManager.find(Bill_Entity.class, idBill);
    }

    public void addSumPrice(Integer idBill, Double totalPrice) {
            Bill_Entity billToAddSumPrice = entityManager.find(Bill_Entity.class, idBill);
            billToAddSumPrice.setTOTAL_PRICE(totalPrice);
            entityManager.merge(billToAddSumPrice);
    }

    public List<Bill_Entity> getFilteredBills(
            LocalDateTime dateExport,
            String typeDate,
            Integer idBill,
            String phone,
            String idUser,
            EnumType.Status_Bill Status,
            String addBill,
            Double totalPrice,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill_Entity> query = cb.createQuery(Bill_Entity.class);
        Root<Bill_Entity> root = query.from(Bill_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;

        if (dateExport != null) {
            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_EXP"), dateExport));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_EXP"), dateExport));
                case "=" -> cb.and(conditions, cb.equal(root.get("DATE_EXP"), dateExport));
                default -> conditions;
            };
            hasConditions = true;
        }
        if (idBill != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BILL"), idBill));
            hasConditions = true;
        }
        if (idUser != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_USER"), idUser));
            hasConditions = true;
        }
        if (Status != null) {
            conditions = cb.and(conditions, cb.equal(root.get("BILL_STATUS"), Status));
            hasConditions = true;
        }
        if (phone != null) {
            conditions = cb.and(conditions, cb.equal(root.get("PHONE_BILL"), phone));
            hasConditions = true;
        }
        if (addBill != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ADD_BILL"), addBill));
            hasConditions = true;
        }
        if (totalPrice != null) {
            conditions = cb.and(conditions, cb.equal(root.get("TOTAL_PRICE"), totalPrice));
            hasConditions = true;
        }
        if (hasConditions) {
            query.where(conditions);
        } else {
            query.select(root);
        }
        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField.toUpperCase());
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


    public Bill_Entity findBill(Integer idBill) {
        return entityManager.find(Bill_Entity.class, idBill);
    }

    public void deleteBill(Integer idBill) {

            Bill_Entity billToDelete = entityManager.find(Bill_Entity.class, idBill);
            if (billToDelete != null && billToDelete.getBILL_STATUS() == EnumType.Status_Bill.PEN) {
                billToDelete.setBILL_STATUS(EnumType.Status_Bill.CANC);
                entityManager.merge(billToDelete);
                return ;
            }
            throw new RuntimeException("Bill does not exist");

    }

    public void changeBillStatus(Integer idBill, EnumType.Status_Bill Status) {
            Bill_Entity billToChangeStatus = entityManager.find(Bill_Entity.class, idBill);
            if (billToChangeStatus != null) {
                billToChangeStatus.setBILL_STATUS(Status);
                entityManager.merge(billToChangeStatus);
                return ;
            }
            throw new RuntimeException("Bill does not exist");
    }


    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
