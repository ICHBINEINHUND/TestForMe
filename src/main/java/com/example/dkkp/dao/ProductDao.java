package com.example.dkkp.dao;

import com.example.dkkp.model.Product_Entity;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ProductDao {
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ProductDao() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }


    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public boolean createProdcut(Product_Entity product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(product);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return false;
            }
            throw new RuntimeException("Error creating product", e);
        }
    }

    public Product_Entity getProductById(String id) {
        String jpql = "SELECT u FROM Product_Entity u WHERE u.ID_SP = :id";
        TypedQuery<Product_Entity> query = entityManager.createQuery(jpql, Product_Entity.class);
        query.setParameter("id", id);
        System.out.println(query.getSingleResult().getNAME_SP());
        ;
        return query.getSingleResult();

    }


    public List<Product_Entity> getFilteredProduct(String id,
                                                   String NAME_SP,
                                                   String DES_SP,
                                                   String ID_CATEGORY,
                                                   Double PRICE_SP,
                                                   String IMAGE_SP,
                                                   Integer VIEW_COUNT,
                                                   Integer QUANTITY,
                                                   Double DISCOUNT,
                                                   List<Integer> IDS_OPTION_VALUES,
                                                   String sortField,
                                                   String sortOrder,
                                                   Integer offset,
                                                   Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Entity> query = cb.createQuery(Product_Entity.class);
        Root<Product_Entity> root = query.from(Product_Entity.class);

        Predicate conditions = cb.conjunction();

        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_SP"), id));
        }
        if (NAME_SP != null) {
            conditions = cb.and(conditions, cb.equal(root.get("NAME_SP"), NAME_SP));
        }
        if (DES_SP != null) {
            conditions = cb.and(conditions, cb.equal(root.get("DES_SP"), DES_SP));
        }
        if (ID_CATEGORY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
        }
        if (PRICE_SP != null) {
            conditions = cb.and(conditions, cb.equal(root.get("PRICE_SP"), PRICE_SP));
        }
        if (IMAGE_SP != null) {
            conditions = cb.and(conditions, cb.equal(root.get("IMAGE_SP"), IMAGE_SP));
        }
        if (VIEW_COUNT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("VIEW_COUNT"), VIEW_COUNT));
        }
        if (QUANTITY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("QUANTITY"), QUANTITY));
        }
        if (DISCOUNT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("DISCOUNT"), DISCOUNT));
        }
        if (IDS_OPTION_VALUES != null) {
            conditions = cb.and(conditions, cb.equal(root.get("IDS_OPTION_VALUES"), IDS_OPTION_VALUES));
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
        TypedQuery<Product_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);
        return typedQuery.getResultList();
    }

    public boolean deleteProduct(String id) {
        Product_Entity product = entityManager.find(Product_Entity.class, id);
        if (product != null) {
            entityManager.remove(product);
            return true;
        }
        throw new RuntimeException("Cant find id to delete");

    }

    public boolean updateProduct(String id,
                                 String NAME_SP,
                                 String DES_SP,
                                 String ID_CATEGORY,
                                 Double PRICE_SP,
                                 String IMAGE_SP,
                                 Integer VIEW_COUNT,
                                 Integer QUANTITY,
                                 Double DISCOUNT,
                                 List<Integer> IDS_OPTION_VALUES) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product_Entity product = entityManager.find(Product_Entity.class, id);
            if (product == null) {
                return false;
            }
            if (NAME_SP != null) product.setNAME_SP(NAME_SP);
            if (DES_SP != null) product.setDES_SP(DES_SP);
            if (ID_CATEGORY != null) product.setID_CATEGORY(ID_CATEGORY);
            if (PRICE_SP != null) product.setPRICE_SP(PRICE_SP);
            if (IMAGE_SP != null) product.setIMAGE_SP(IMAGE_SP);
            if (VIEW_COUNT != null) product.setVIEW_COUNT(VIEW_COUNT);
            if (QUANTITY != null) product.setQUANTITY(QUANTITY);
            if (DISCOUNT != null) product.setDISCOUNT(DISCOUNT);
            if (IDS_OPTION_VALUES != null) product.setIDS_OPTION_VALUES(IDS_OPTION_VALUES);
            entityManager.merge(product);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error occurred while updating product", e);
        }
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
