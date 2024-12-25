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

    public ImportDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Thêm phương thức getEntityManager
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void createImport(Import_Entity importE) {
            entityManager.persist(importE);
    }



    public List<Import_Entity> getFilteredImports(LocalDateTime DATE_IMP, String typeDate, String ID_IMP, Boolean IS_AVAILABLE, Integer ID_REPLACE,Double TOTAL_PRICE, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Import_Entity> query = cb.createQuery(Import_Entity.class);
        Root<Import_Entity> root = query.from(Import_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;
        if (typeDate != null) {
            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_IMP"), DATE_IMP));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_IMP"), DATE_IMP));
                case "=" -> cb.and(conditions, cb.equal(root.get("DATE_IMP"), DATE_IMP));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("DATE_IMP"), DATE_IMP));
                case "=>" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("DATE_IMP"), DATE_IMP));
                default -> conditions;
            };
            hasConditions = true;
        }

        if (ID_IMP != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_IMP"), ID_IMP));
            hasConditions = true;
        }
        if (IS_AVAILABLE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("STATUS"), IS_AVAILABLE));
            hasConditions = true;
        }
        if (ID_REPLACE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_REPLACE"), ID_REPLACE));
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
        TypedQuery<Import_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);  // vị trí bắt đầu
        if (setOff != null) typedQuery.setMaxResults(setOff); // Số lượng bản ghi mỗi lần
        return typedQuery.getResultList();
    }

    public void addSumPrice(String ID_IMP, Double sumPrice) {
            Import_Entity importToAddSumPrice = entityManager.find(Import_Entity.class, ID_IMP);
            if (importToAddSumPrice == null) {
                throw new RuntimeException("Can find import general to add total price");
            }
            importToAddSumPrice.setTOTAL_PRICE(sumPrice);
            entityManager.merge(importToAddSumPrice);
    }

    public boolean checkImport(String ID_IMP) {
            Import_Entity importToCheck = entityManager.find(Import_Entity.class, ID_IMP);
            return importToCheck.getIS_AVAILABLE();
    }

    public void deleteImport(String ID_IMP) {
            Import_Entity importToDelete = entityManager.find(Import_Entity.class, ID_IMP);
            if (importToDelete != null) {
                importToDelete.setIS_AVAILABLE(false);
                entityManager.merge(importToDelete);
                return ;
            }
            throw new RuntimeException("Can find import general to delete");
    }

    public void updateDescriptionImport(String ID_IMP, String description) {
            Import_Entity importToEdit = entityManager.find(Import_Entity.class, ID_IMP);
            if (importToEdit == null) {
                throw new RuntimeException("Can find import general to update");
            }
            importToEdit.setDESCRIPTION(description);
            entityManager.merge(importToEdit);
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
