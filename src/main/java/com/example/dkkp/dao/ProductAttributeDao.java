package com.example.dkkp.dao;

import com.example.dkkp.model.Product_Attribute_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ProductAttributeDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ProductAttributeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public boolean createProductAttribute(Product_Attribute_Entity productAttribute) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(productAttribute);
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

    public List<Product_Attribute_Entity> getFilteredProductAttribute(Integer ID_ATTRIBUTE, String NAME_ATTRIBUTE, Integer ID_CATEGORY, String sortField, String sortOrder, Integer setOff, Integer offSet) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Attribute_Entity> query = cb.createQuery(Product_Attribute_Entity.class);
        Root<Product_Attribute_Entity> root = query.from(Product_Attribute_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;
        if (ID_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_OPTION"), ID_ATTRIBUTE));
            hasConditions = true;
        }
        if (NAME_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("NAME_OPTION"), NAME_ATTRIBUTE));
            hasConditions = true;
        }
        if (ID_CATEGORY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
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

        TypedQuery<Product_Attribute_Entity> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(offSet);
        typedQuery.setMaxResults(setOff);
        return entityManager.createQuery(query).getResultList();
    }

    public void deleteProductAttributeById(Integer ID_ATTRIBUTE) {

        Product_Attribute_Entity productAttribute = entityManager.find(Product_Attribute_Entity.class, ID_ATTRIBUTE);
        if (productAttribute != null) {
            try {
                entityManager.remove(productAttribute);
                return;
            } catch (RuntimeException e) {
                throw new RuntimeException("please delete all attribute value belong this product attribute:" + ID_ATTRIBUTE, e);
            }
        }
        throw new RuntimeException("Cant find id - attribute to delete");
    }


    public void updateProductAttribute(Integer ID_ATTRIBUTE, Integer ID_CATEGORY, String NAME_ATTRIBUTE) {
        Product_Attribute_Entity productAttribute = entityManager.find(Product_Attribute_Entity.class, ID_ATTRIBUTE);
        if (productAttribute == null) {
            throw new RuntimeException("Cant not find Product Option to update with ID: " + ID_ATTRIBUTE);
        }
        if (ID_CATEGORY != null) productAttribute.setID_CATEGORY(ID_CATEGORY);
        if (NAME_ATTRIBUTE != null) productAttribute.setNAME_ATTRIBUTE(NAME_ATTRIBUTE);
        try {
            entityManager.merge(productAttribute);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while updating Product Option with ID_BASEPRODUCT: " + ID_ATTRIBUTE, e);
        }
    }
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
