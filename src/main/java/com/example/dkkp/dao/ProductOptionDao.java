package com.example.dkkp.dao;

import com.example.dkkp.model.Bill_Entity;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Product_Option_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductOptionDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public ProductOptionDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  public boolean createProductOption(Product_Option_Entity productOption) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(productOption);
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

  public List<Bill_Entity> getFilteredBills( String id, String name,String type, String idBaseProduct, String sortField, String sortOrder, Integer offset, Integer setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Bill_Entity> query = cb.createQuery(Bill_Entity.class);
    Root<Bill_Entity> root = query.from(Bill_Entity.class);

    Predicate conditions = cb.conjunction();

    if (id != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_OPTION"), id));
    }
    if (name != null) {
      conditions = cb.and(conditions, cb.equal(root.get("NAME_OPTION"), name));
    }
    if (type != null) {
      conditions = cb.and(conditions, cb.equal(root.get("TYPE"), type));
    }
    if (idBaseProduct != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_BASEPRODUCT"), idBaseProduct));
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
    TypedQuery<Bill_Entity> typedQuery = entityManager.createQuery(query);
    if(offset !=null) typedQuery.setFirstResult(offset);
    if(setOff !=null) typedQuery.setMaxResults(setOff);
    return typedQuery.getResultList();
  }

  public boolean deleteProductOptionById(String id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Product_Option_Entity productOption = entityManager.find(Product_Option_Entity.class, id);
      if (productOption != null ) {
        entityManager.remove(productOption);
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
  public boolean deleteProductOptionByProductId(String idProduct) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();

      List<Product_Option_Entity> productOptions = entityManager.createQuery(
                      "SELECT po FROM Product_Option_Entity po WHERE po.ID_BASEPRODUCT = :idProduct", Product_Option_Entity.class)
              .setParameter("idProduct", idProduct)
              .getResultList();

      if (!productOptions.isEmpty()) {
        for (Product_Option_Entity productOption : productOptions) {
          try {
            entityManager.remove(productOption);
          } catch (RuntimeException e) {
            transaction.rollback();
            throw new RuntimeException("Error occurred while deleting Product Option with ID_BASEPRODUCT: " + idProduct, e);
          }
        }

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

  public boolean updateCategory(String id, String name, String type, String idBaseProduct) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Product_Option_Entity productOption = entityManager.find(Product_Option_Entity.class, id);
      if (productOption == null) {
        return false;
      }
      if (name != null) productOption.setNAME_OPTION(name);
      if (type != null) productOption.setTYPE(type);
      if (idBaseProduct != null) productOption.setID_BASEPRODUCT(idBaseProduct);
      entityManager.merge(productOption);
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
