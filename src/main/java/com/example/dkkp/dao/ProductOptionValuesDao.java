package com.example.dkkp.dao;

import com.example.dkkp.model.Product_Option_Values_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ProductOptionValuesDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ProductOptionValuesDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public boolean createProductOptionValues(Product_Option_Values_Entity optionValue) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(optionValue);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return false;
            }
            throw new RuntimeException("Error creating option values", e);
        }
    }

    public boolean updateOptionValue(Integer id,
                                     String value,
                                     Integer idOption,
                                     Integer idFinalProduct) {
        EntityTransaction transaction = entityManager.getTransaction();

        Product_Option_Values_Entity productOption_values_entity = entityManager.find(Product_Option_Values_Entity.class, id);
        if (productOption_values_entity == null) {
            return false;
        }
        if (value != null) productOption_values_entity.setVALUE(value);
        if (idOption != null) productOption_values_entity.setID_OPTION(idOption);
        if (idFinalProduct != null) productOption_values_entity.setID_FINAL_PRODUCT(idFinalProduct);
        entityManager.merge(productOption_values_entity);
        transaction.commit();
        return true;
    }

    public List<Product_Option_Values_Entity> getFilteredProductOptionValue(Integer id, Integer idOption, String value, Integer idFinalProduct, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Option_Values_Entity> query = cb.createQuery(Product_Option_Values_Entity.class);
        Root<Product_Option_Values_Entity> root = query.from(Product_Option_Values_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;
        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID"), id));
            hasConditions = true;
        }
        if (idOption != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_OPTION"), idOption));
            hasConditions = true;
        }
        if (value != null) {
            conditions = cb.and(conditions, cb.equal(root.get("VALUE"), value));
            hasConditions = true;
        }
        if (idFinalProduct != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_FINAL_PRODUCT"), idFinalProduct));
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
        TypedQuery<Product_Option_Values_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);
        return typedQuery.getResultList();
    }

    public void deleteOptionValues(Integer id, Integer idOption, Integer idFinalProduct) {
        StringBuilder queryStr = new StringBuilder("SELECT po FROM Product_Option_Values_Entity po WHERE 1=1");
        var query = entityManager.createQuery(queryStr.toString(), Product_Option_Values_Entity.class);

        if (id != null) {
            queryStr.append(" AND po.ID = :id");
            query.setParameter("id", id);
        } else if (idOption != null) {
            queryStr.append(" AND po.ID_OPTION = :idOption");
            query.setParameter("idOption", idOption);
        } else if (idFinalProduct != null) {
            queryStr.append(" AND po.ID_FINAL_PRODUCT = :idFinalProduct");
            query.setParameter("idFinalProduct", idFinalProduct);
        }

        List<Product_Option_Values_Entity> optionValues = query.getResultList();
        if (!optionValues.isEmpty()) {
            for (Product_Option_Values_Entity optionValue : optionValues) {
                try {
                    entityManager.remove(optionValue);
                    return;
                } catch (RuntimeException e) {
                    throw new RuntimeException("Error occurred while deleting Option Value", e);
                }
            }
        }
        throw new RuntimeException("Cannot find Option Values to delete");
    }


    public boolean updateProductOptionValues(Integer id, String value, Integer idOption,Integer idFinalProduct) {
        EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Product_Option_Values_Entity optionValue = entityManager.find(Product_Option_Values_Entity.class, id);
            if (optionValue == null) {
                return false;
            }
            if (value != null) optionValue.setVALUE(value);
            if (idOption != null) optionValue.setID_OPTION(idOption);
            if (idFinalProduct != null) optionValue.setID_FINAL_PRODUCT(idFinalProduct);
            entityManager.merge(optionValue);
            transaction.commit();
            return true;
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
