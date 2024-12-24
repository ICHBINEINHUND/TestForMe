package com.example.dkkp.dao;

import com.example.dkkp.model.Product_Base_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductBaseDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ProductBaseDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void createProductBase(Product_Base_Entity product) {
            entityManager.persist(product);
    }

    public Product_Base_Entity getProductBaseById(Integer ID_BASE_PRODUCT) {
        String jpql = "SELECT u FROM Product_Base_Entity u WHERE u.ID_BASE_PRODUCT = :ID_BASE_PRODUCT";
        TypedQuery<Product_Base_Entity> query = entityManager.createQuery(jpql, Product_Base_Entity.class);
        query.setParameter("ID_BASE_PRODUCT", ID_BASE_PRODUCT);
        return query.getSingleResult();

    }


    public List<Product_Base_Entity> getFilteredProductBase(Integer ID_BASE_PRODUCT,
                                                            String NAME_PRODUCT,
                                                            Integer ID_CATEGORY,
                                                            Integer ID_BRAND,
                                                            Integer TOTAL_QUANTITY,
                                                            LocalDateTime DATE_RELEASE,
                                                            String typeDate,
                                                            String sortField,
                                                            String sortOrder,
                                                            Integer offset,
                                                            Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Base_Entity> query = cb.createQuery(Product_Base_Entity.class);
        Root<Product_Base_Entity> root = query.from(Product_Base_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;

        if (DATE_RELEASE != null) {
            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_RELEASE"), DATE_RELEASE));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "=" -> cb.and(conditions, cb.equal(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "=>" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("DATE_RELEASE"), DATE_RELEASE));
                default -> conditions;
            };
            hasConditions = true;
        }
        if (ID_BASE_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_SP"), ID_BASE_PRODUCT));
            hasConditions = true;
        }
        if (NAME_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("NAME_PRODUCT"), NAME_PRODUCT));
            hasConditions = true;
        }
        if (ID_CATEGORY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
            hasConditions = true;
        }
        if (ID_BRAND != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BRAND"), ID_BRAND));
            hasConditions = true;
        }
        if (TOTAL_QUANTITY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
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

        TypedQuery<Product_Base_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);
        return typedQuery.getResultList();
    }

    public void deleteProductBase(Integer ID_BASE_PRODUCT) {
        Product_Base_Entity product = entityManager.find(Product_Base_Entity.class, ID_BASE_PRODUCT);
        if (product != null) {
            try {
                entityManager.remove(product);
                return;
            } catch (Exception e) {
                throw new RuntimeException("Fail to delete base product attribute please delete all row in attribute values first");
            }
        }
            throw new RuntimeException("Cant find ID_BASE_PRODUCT to delete");
    }

    public boolean updateProductBase(Integer ID_BASE_PRODUCT,
                                     String NAME_PRODUCT,
                                     String DES_PRODUCT,
                                     Integer ID_CATEGORY,
                                     Integer VIEW_COUNT,
                                     Integer TOTAL_QUANTITY,
                                     Integer ID_Brand,
                                     LocalDateTime DATE_RELEASE
    ) {
        try {
            Product_Base_Entity product = entityManager.find(Product_Base_Entity.class, ID_BASE_PRODUCT);
            if (product == null) {
                return false;
            }
            if (NAME_PRODUCT != null) product.setNAME_PRODUCT(NAME_PRODUCT);
            if (DES_PRODUCT != null) product.setDES_PRODUCT(DES_PRODUCT);
            if (ID_CATEGORY != null) product.setID_CATEGORY(ID_CATEGORY);
            if (ID_Brand != null) product.setID_BRAND(ID_Brand);
            if (VIEW_COUNT != null) product.setVIEW_COUNT(VIEW_COUNT);
            if (TOTAL_QUANTITY != null) product.setQUANTITY(TOTAL_QUANTITY);
            if (DATE_RELEASE != null) product.setDATE_RELEASE(DATE_RELEASE);
            entityManager.merge(product);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while updating product base", e);
        }
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
