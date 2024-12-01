package com.example.dkkp.dao;

import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.Report_Bug;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.*;

import java.util.List;

public class BrandDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public BrandDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createBrand(Brand_Entity brand) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(brand);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Brand_Entity> getAllBrand() {
    String jpql = "SELECT u FROM Brand_Entity u";
    TypedQuery<Brand_Entity> query = entityManager.createQuery(jpql, Brand_Entity.class);
    return query.getResultList();
  }

  public List<Brand_Entity> getBrandByID(String id) {
    String jpql = "SELECT u FROM Brand_Entity u WHERE u.ID_BRAND = :id";
    TypedQuery<Brand_Entity> query = entityManager.createQuery(jpql, Brand_Entity.class);
    query.setParameter("id", id);
    return query.getResultList();
  }


  public boolean updateBrand(String id, String name,String des) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Brand_Entity user = entityManager.find(Brand_Entity.class, id);
      if (user == null) {
        return false;
      }
      if (des != null) user.setDETAIL(des);
      if (name != null) user.setNAME_BRAND(name);
      entityManager.merge(user);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public boolean deleteBrandById(String brandId) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Brand_Entity brandToDelete = entityManager.find(Brand_Entity.class, brandId);
      if (brandToDelete != null) {
        entityManager.remove(brandToDelete);
        transaction.commit();
        return true;
      }
      transaction.commit();
      return false;
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