package com.example.dkkp.dao;

import com.example.dkkp.model.*;
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

    public ImportDetailDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createImportDetail(List<Import_Detail_Entity> listImportDetail) throws SQLException {

            int batchSize = 10;
            for (int i = 0; i < listImportDetail.size(); i++) {
                Import_Detail_Entity importDetail = listImportDetail.get(i);
                entityManager.persist(importDetail);
                if (i % batchSize == 0 && i > 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }

    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public List<Import_Detail_Entity> getFilteredImportDetails(Integer ID_IMPD, String ID_IMPORT, Integer ID_FINAL_PRODUCT,String NAME_FINAL_PRODUCT,Boolean IS_AVAILABLE,Integer ID_BASE_PRODUCT,String NAME_BASE_PRODUCT,Integer QUANTITY,Double UNIT_PRICE,Double TOTAL_PRICE, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Import_Detail_Entity> query = cb.createQuery(Import_Detail_Entity.class);
        Root<Import_Detail_Entity> root = query.from(Import_Detail_Entity.class);
        Join<Import_Detail_Entity, Product_Base_Entity> productBaseJoin = root.join("product_base", JoinType.INNER);
        Join<Import_Detail_Entity, Product_Final_Entity> productFinalJoin = root.join("product_final", JoinType.INNER);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;

        if (ID_IMPD != null ) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_IMPD"), ID_IMPD));
            hasConditions = true;
        }
        if (ID_IMPORT != null ) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_IMPORT"), ID_IMPORT));
            hasConditions = true;
        }
        if (ID_FINAL_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_FINAL_PRODUCT"), ID_FINAL_PRODUCT));
            hasConditions = true;
        }
        if (NAME_FINAL_PRODUCT != null) {
            conditions = cb.and(conditions, cb.like(productFinalJoin.get("NAME_PRODUCT"), "%" + NAME_FINAL_PRODUCT + "%"));
            hasConditions = true;
        }
        if (ID_BASE_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
            hasConditions = true;
        }
        if (NAME_BASE_PRODUCT != null) {
            conditions = cb.and(conditions, cb.like(productBaseJoin.get("NAME_PRODUCT"), "%" + NAME_BASE_PRODUCT + "%"));
            hasConditions = true;
        }
        if (QUANTITY != null ) {
            conditions = cb.and(conditions, cb.equal(root.get("QUANTITY"), QUANTITY));
            hasConditions = true;
        }
        if (IS_AVAILABLE != null ) {
            conditions = cb.and(conditions, cb.equal(root.get("IS_AVAILABLE"), IS_AVAILABLE));
            hasConditions = true;
        }
        if (UNIT_PRICE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("UNIT_PRICE"), UNIT_PRICE));
            hasConditions = true;
        }
        if (TOTAL_PRICE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("TOTAL_PRICE"), TOTAL_PRICE));
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

        query.select(cb.construct(
                Import_Detail_Entity.class,
                root.get("ID_IMPD"),
                root.get("ID_IMPORT"),
                root.get("IS_AVAILABLE"),
                root.get("ID_BASE_PRODUCT"),
                root.get("ID_FINAL_PRODUCT"),
                root.get("QUANTITY"),
                root.get("UNIT_PRICE"),
                root.get("DESCRIPTION"),
                productBaseJoin.get("NAME_PRODUCT"),
                productFinalJoin.get("NAME_PRODUCT")
        ));

        TypedQuery<Import_Detail_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }


    public boolean deleteImportDetailByIdImport(String ID_IMPORT) {
            Query query = entityManager.createQuery(
                    "UPDATE Import_Detail_Entity e SET e.IS_AVAILABLE = false WHERE e.ID_IMPORT = :ID_IMPORT"
            );
            query.setParameter("ID_IMPORT", ID_IMPORT);
            int rowsUpdated = query.executeUpdate();
            return rowsUpdated > 0;
    }


    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
