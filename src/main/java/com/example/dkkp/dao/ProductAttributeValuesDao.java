package com.example.dkkp.dao;

import com.example.dkkp.model.Product_Attribute_Values_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ProductAttributeValuesDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ProductAttributeValuesDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void createProductAttributeValues(Product_Attribute_Values_Entity attributeValue) {
        try {
            entityManager.persist(attributeValue);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating attribute value", e);
        }
    }

    public Product_Attribute_Values_Entity getProductAttributeValuesById(Integer ID) {
        String jpql = "SELECT u FROM Product_Attribute_Values_Entity u WHERE u.ID = :ID";
        TypedQuery<Product_Attribute_Values_Entity> query = entityManager.createQuery(jpql, Product_Attribute_Values_Entity.class);
        query.setParameter("ID", ID);
        return query.getSingleResult();

    }


    public List<Product_Attribute_Values_Entity> getFilteredProductAttributeValues(Integer ID,
                                                                                   String VALUE,
                                                                                   Integer ID_ATTRIBUTE,
                                                                                   Integer ID_BASE_PRODUCT,
                                                                                   String sortField,
                                                                                   String sortOrder,
                                                                                   Integer offset,
                                                                                   Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Attribute_Values_Entity> query = cb.createQuery(Product_Attribute_Values_Entity.class);
        Root<Product_Attribute_Values_Entity> root = query.from(Product_Attribute_Values_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;

        if (ID != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID"), ID));
            hasConditions = true;
        }
        if (VALUE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("VALUE"), VALUE));
            hasConditions = true;
        }
        if (ID_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_ATTRIBUTE"), ID_ATTRIBUTE));
            hasConditions = true;
        }
        if (ID_BASE_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
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
        TypedQuery<Product_Attribute_Values_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);
        return typedQuery.getResultList();
    }

    public void deleteAttributeValues(Integer id, Integer ID_ATTRIBUTE, Integer ID_BASE_PRODUCT) {
        StringBuilder queryStr = new StringBuilder("SELECT po FROM Product_Attribute_Values_Entity po WHERE 1=1");

        var query = entityManager.createQuery(queryStr.toString(), Product_Attribute_Values_Entity.class);
        if (id != null) {
            queryStr.append(" AND po.ID = :id");
        } else if (ID_BASE_PRODUCT != null) {
            queryStr.append(" AND po.ID_BASE_PRODUCT = :ID_BASE_PRODUCT");
        } else if (ID_ATTRIBUTE != null) {
            queryStr.append(" AND po.ID_ATTRIBUTE = :ID_ATTRIBUTE");
        }
        if (id != null) {
            query.setParameter("id", id);
        } else if (ID_BASE_PRODUCT != null) {
            query.setParameter("ID_BASE_PRODUCT", ID_BASE_PRODUCT);
        } else if (ID_ATTRIBUTE != null) {
            query.setParameter("ID_ATTRIBUTE", ID_ATTRIBUTE);
        }
        List<Product_Attribute_Values_Entity> attributeValues = query.getResultList();
        if (!attributeValues.isEmpty()) {
            for (Product_Attribute_Values_Entity optionValue : attributeValues) {
                entityManager.remove(optionValue);
            }
        }
    }


    public void updateProductAttributeValues(Integer ID,
                                             String VALUE,
                                             Integer ID_BASE_PRODUCT,
                                             Integer ID_ATTRIBUTE
    ) {
        Product_Attribute_Values_Entity attributeValue = entityManager.find(Product_Attribute_Values_Entity.class, ID);
        if (attributeValue == null) {
            throw new RuntimeException("Cannot find Product Attribute Value with ID: " + ID);
        }
        if (VALUE != null) attributeValue.setVALUE(VALUE);
        if (ID_ATTRIBUTE != null) attributeValue.setID_ATTRIBUTE(ID_ATTRIBUTE);
        if (ID_BASE_PRODUCT != null) attributeValue.setID_BASE_PRODUCT(ID_BASE_PRODUCT);
        entityManager.merge(attributeValue);
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
