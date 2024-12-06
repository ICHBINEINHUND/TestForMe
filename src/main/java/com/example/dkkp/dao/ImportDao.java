package com.example.dkkp.dao;

import com.example.dkkp.model.EnumType;
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

    public boolean createImport(Import_Entity importE) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(importE);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return false;
            }
            throw e;
        }
    }

    public List<Import_Entity> getAllImport() {
        String jpql = "SELECT u FROM Import_Detail_Entity u";
        TypedQuery<Import_Entity> query = entityManager.createQuery(jpql, Import_Entity.class);
        return query.getResultList();
    }

    public List<Import_Entity> getFilteredImports(LocalDateTime dateJoin, String typeDate, String id, Boolean status, String idReplace, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Import_Entity> query = cb.createQuery(Import_Entity.class);
        Root<Import_Entity> root = query.from(Import_Entity.class);

        Predicate conditions = cb.conjunction();

        if (typeDate != null) {

            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_IMP"), dateJoin));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_IMP"), dateJoin));
                case "=" -> cb.and(conditions, cb.equal(root.get("DATE_IMP"), dateJoin));
                default -> conditions;
            };
        }

        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_IMP"), id));
        }
        if (status != null) {
            conditions = cb.and(conditions, cb.equal(root.get("STATUS"), status));
        }
        if (idReplace != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_REPLACE"), idReplace));
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
        TypedQuery<Import_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);  // vị trí bắt đầu
        if (setOff != null) typedQuery.setMaxResults(setOff); // Số lượng bản ghi mỗi lần

        return typedQuery.getResultList();
    }

    public boolean checkImport(String id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Import_Entity importToCheck = entityManager.find(Import_Entity.class, id);
            transaction.commit();
            return importToCheck.getSTATUS();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return false;
            }
            return false;
        }
    }

    public boolean deleteImport(String id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Import_Entity importToDelete = entityManager.find(Import_Entity.class, id);
            if (importToDelete != null) {
                importToDelete.setSTATUS(false);
                entityManager.merge(importToDelete);
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
