package com.example.dkkp.dao;

import com.example.dkkp.model.Product_Base_Entity;
import com.example.dkkp.model.Product_Final_Entity;
import com.example.dkkp.model.Product_Option_Entity;
import com.example.dkkp.model.Product_Option_Values_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ProductOptionValuesDao {
    private final EntityManager entityManager;


    public ProductOptionValuesDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void createProductOptionValues(Product_Option_Values_Entity optionValue) {
        entityManager.persist(optionValue);
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

    public List<Product_Option_Values_Entity> getFilteredProductOptionValue(Integer id, Integer idOption, String NAME_OPTION, String value, Integer idFinalProduct, String NAME_PRODUCT, String sortField, String sortOrder, Integer offset, Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Option_Values_Entity> query = cb.createQuery(Product_Option_Values_Entity.class);
        Root<Product_Option_Values_Entity> root = query.from(Product_Option_Values_Entity.class);

        Join<Product_Option_Values_Entity, Product_Option_Entity> productOptionjoin = root.join("product_options", JoinType.INNER);
        Join<Product_Option_Values_Entity, Product_Final_Entity> productFinaljoin = root.join("product_final", JoinType.INNER);

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
        if (NAME_OPTION != null) {
            conditions = cb.and(conditions, cb.like(productOptionjoin.get("NAME_OPTION"), "%" + NAME_OPTION + "%"));
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
        if (NAME_PRODUCT != null) {
            conditions = cb.and(conditions, cb.like(productFinaljoin.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
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
                Product_Option_Values_Entity.class,
                root.get("ID"),
                root.get("ID_OPTION"),
                root.get("VALUE"),
                root.get("ID_FINAL_PRODUCT"),
                productOptionjoin.get("NAME_OPTION"),
                productOptionjoin.get("NAME_PRODUCT")
        ));
        TypedQuery<Product_Option_Values_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);
        return typedQuery.getResultList();
    }
    public Integer getFilteredProductOptionValueCount(Integer id, Integer idOption, String NAME_OPTION, String value, Integer idFinalProduct, String NAME_PRODUCT) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product_Option_Values_Entity> root = query.from(Product_Option_Values_Entity.class);

        Join<Product_Option_Values_Entity, Product_Option_Entity> productOptionjoin = root.join("product_options", JoinType.INNER);
        Join<Product_Option_Values_Entity, Product_Final_Entity> productFinaljoin = root.join("product_final", JoinType.INNER);

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
        if (NAME_OPTION != null) {
            conditions = cb.and(conditions, cb.like(productOptionjoin.get("NAME_OPTION"), "%" + NAME_OPTION + "%"));
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
        if (NAME_PRODUCT != null) {
            conditions = cb.and(conditions, cb.like(productFinaljoin.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
            hasConditions = true;
        }

        if (hasConditions) {
            query.where(conditions);
        }

        query.select(cb.count(root));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        Long result = typedQuery.getSingleResult();
        return result != null ? result.intValue() : 0;
    }


    public void deleteOptionValues(Integer ID, Integer ID_OPTION, Integer ID_FINAL_PRODUCT) {
        // Bắt đầu xây dựng câu truy vấn
        StringBuilder queryStr = new StringBuilder("SELECT po FROM Product_Option_Values_Entity po WHERE 1=1");
        if (ID != null) {
            queryStr.append(" AND po.ID = :ID");
        } else if (ID_OPTION != null) {
            queryStr.append(" AND po.ID_OPTION = :ID_OPTION");
        } else if (ID_FINAL_PRODUCT != null) {
            queryStr.append(" AND po.ID_FINAL_PRODUCT = :ID_FINAL_PRODUCT");
        }
        var query = entityManager.createQuery(queryStr.toString(), Product_Option_Values_Entity.class);
        if (ID != null) {
            query.setParameter("ID", ID);
        } else if (ID_OPTION != null) {
            query.setParameter("ID_OPTION", ID_OPTION);
        } else if (ID_FINAL_PRODUCT != null) {
            query.setParameter("ID_FINAL_PRODUCT", ID_FINAL_PRODUCT);
        }
        List<Product_Option_Values_Entity> optionValues = query.getResultList();
        if (!optionValues.isEmpty()) {
            for (Product_Option_Values_Entity optionValue : optionValues) {
                try {
                    entityManager.remove(optionValue);
                } catch (RuntimeException e) {
                    throw new RuntimeException("Error occurred while deleting Option Value", e);
                }
            }
        } else {
            throw new RuntimeException("Cannot find Option Values to delete");
        }
    }


    public void updateProductOptionValues(Integer id, String value, Integer idOption, Integer idFinalProduct) {

        Product_Option_Values_Entity optionValue = entityManager.find(Product_Option_Values_Entity.class, id);
        if (optionValue == null) {
            throw new RuntimeException("Can not find option value to delete");
        }
        if (value != null) optionValue.setVALUE(value);
        if (idOption != null) optionValue.setID_OPTION(idOption);
        if (idFinalProduct != null) optionValue.setID_FINAL_PRODUCT(idFinalProduct);
        entityManager.merge(optionValue);
    }


}
