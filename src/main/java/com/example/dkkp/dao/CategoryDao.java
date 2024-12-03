package com.example.dkkp.dao;

import com.example.dkkp.model.Category_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CategoryDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public CategoryDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createCategory(Category_Entity category) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(category);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Category_Entity> getFilteredCategories(
          String id,
          String name,
          String idParent,
          String idBrand,
          Boolean isBaseProduct,
          String sortField,
          String sortOrder,
          int offset,
          int setOff
  ) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Category_Entity> query = cb.createQuery(Category_Entity.class);
    Root<Category_Entity> root = query.from(Category_Entity.class);

    Predicate conditions = cb.conjunction();

    if (id != null && !id.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), id));
    }
    if (name != null && !name.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("NAME_CATEGORY"), name));
    }
    if (idParent != null && !idParent.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_PARENT"), idParent));
    }
    if (idBrand != null && !idBrand.trim().isEmpty()) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_BRAND"), idBrand));
    }
    if (isBaseProduct != null) {
      conditions = cb.and(conditions, cb.equal(root.get("IS_BASE_PRODUCT"), isBaseProduct));
    }

    query.where(conditions);

    if (sortField != null && sortOrder != null) {
      Path<?> sortPath = root.get(sortField);
      if ("desc".equalsIgnoreCase(sortOrder)) {
        query.orderBy(cb.desc(sortPath));
      } else {
        query.orderBy(cb.asc(sortPath));
      }
    }

    TypedQuery<Category_Entity> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult(offset);
    typedQuery.setMaxResults(setOff);

    return typedQuery.getResultList();
  }

  public boolean updateCategory(String id, String name, String idParent, String idBrand, boolean isProduct) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Category_Entity category = entityManager.find(Category_Entity.class, id);
      if (category == null) {
        return false;
      }
      if (name != null) category.setNAME_CATEGORY(name);
      if (idParent != null) category.setID_PARENT(idParent);
      if (idBrand != null) category.setID_BRAND(idBrand);
      category.setIS_BASE_PRODUCT(isProduct);
      entityManager.merge(category);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public boolean deleteCategoryById(String categoryId) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Category_Entity categoryToDelete = entityManager.find(Category_Entity.class, categoryId);
      if (categoryToDelete != null) {
        entityManager.remove(categoryToDelete);
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
