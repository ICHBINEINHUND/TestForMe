package com.example.dkkp.dao;

import com.example.dkkp.model.Brand_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class BrandDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public BrandDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void createBrand(Brand_Entity brand) {
            entityManager.persist(brand);
    }

    public List<Brand_Entity> getFilteredBrand(Integer id, String name, String sortField, String sortOrder) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand_Entity> query = cb.createQuery(Brand_Entity.class);
        Root<Brand_Entity> root = query.from(Brand_Entity.class);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;

        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BRAND"), id));
            hasConditions = true;
        }

        if (name != null && !name.trim().isEmpty()) {
            conditions = cb.and(conditions, cb.equal(root.get("NAME_BRAND"), name));
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

        TypedQuery<Brand_Entity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }


    public void updateBrand(Integer id, String name, String detail) {
            Brand_Entity user = entityManager.find(Brand_Entity.class, id);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            if (detail != null) user.setDETAIL(detail);
            if (name != null) user.setNAME_BRAND(name);
            entityManager.merge(user);
    }

    public void deleteBrandById(Integer brandId) {
            Brand_Entity brandToDelete = entityManager.find(Brand_Entity.class, brandId);
            if (brandToDelete != null) {
                entityManager.remove(brandToDelete);
                return ;
            }
            throw new RuntimeException("Brand not found");
    }


    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}