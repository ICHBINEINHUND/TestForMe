package com.example.dkkp.dao;

import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Report_Bug;
import jakarta.persistence.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

  public List<Category_Entity> getAllCategory() {
    String jpql = "SELECT u FROM Category_Entity u";
    TypedQuery<Category_Entity> query = entityManager.createQuery(jpql, Category_Entity.class);
    return query.getResultList();
  }

  public List<Category_Entity> getCategoryByID(String id) {
    String jpql = "SELECT u FROM Category_Entity u WHERE u.ID_CATEGORY = :id";
    TypedQuery<Category_Entity> query = entityManager.createQuery(jpql, Category_Entity.class);
    query.setParameter("id", id);
    return query.getResultList();
  }
  public List<Category_Entity> getCategoryByName(String name) {
    String jpql = "SELECT u FROM Category_Entity u WHERE u.NAME_CATEGORY = :name";
    TypedQuery<Category_Entity> query = entityManager.createQuery(jpql, Category_Entity.class);
    query.setParameter("name", name);
    return query.getResultList();
  }
  public List<Category_Entity> getCategoryByID_PARENT(String parent_id) {
    String jpql = "SELECT u FROM Category_Entity u WHERE u.ID_PARENT = :parent_id";
    TypedQuery<Category_Entity> query = entityManager.createQuery(jpql, Category_Entity.class);
    query.setParameter("parent_id", parent_id);
    return query.getResultList();
  }
  public List<Category_Entity> getCategoryByID_BRAND(String brand_id) {
    String jpql = "SELECT u FROM Category_Entity u WHERE u.ID_BRAND = :brand_id";
    TypedQuery<Category_Entity> query = entityManager.createQuery(jpql, Category_Entity.class);
    query.setParameter("brand_id", brand_id);
    return query.getResultList();
  }
  public List<Category_Entity> getCategoryByIS_BASE_PRODUCT(boolean is) {
    String jpql = "SELECT u FROM Category_Entity u WHERE u.IS_BASE_PRODUCT = :is";
    TypedQuery<Category_Entity> query = entityManager.createQuery(jpql, Category_Entity.class);
    return query.getResultList();
  }


  public List<Category_Entity> sortResults(List<Category_Entity> category, String sortField, String sortOrder) {
    switch (sortField) {
      case "id":
        return sortById(category, sortOrder);
      case "date":
        return sortByName(category, sortOrder);
      case "userId":
        return sortByBaseProduct(category, sortOrder);
      case "parentId":
        return sortByParent(category, sortOrder);
        case "brandId":
        return sortByBrand(category, sortOrder);
      default:
        throw new IllegalArgumentException("Invalid sort field: " + sortField);
    }
  }
  public List<Category_Entity> sortById(List<Category_Entity> category, String sortOrder) {
    return category.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Category_Entity::getID_CATEGORY).reversed()
                    : Comparator.comparing(Category_Entity::getID_CATEGORY))
            .collect(Collectors.toList());
  }
  public List<Category_Entity> sortByName(List<Category_Entity> category, String sortOrder) {
    return category.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Category_Entity::getNAME_CATEGORY).reversed()
                    : Comparator.comparing(Category_Entity::getNAME_CATEGORY))
            .collect(Collectors.toList());
  }

  public List<Category_Entity> sortByBaseProduct(List<Category_Entity> category, String sortOrder) {
    return category.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Category_Entity::getIS_BASE_PRODUCT).reversed()
                    : Comparator.comparing(Category_Entity::getIS_BASE_PRODUCT))
            .collect(Collectors.toList());
  }

  public List<Category_Entity> sortByParent(List<Category_Entity> category, String sortOrder) {
    return category.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Category_Entity::getID_PARENT).reversed()
                    : Comparator.comparing(Category_Entity::getID_PARENT))
            .collect(Collectors.toList());
  }

  public List<Category_Entity> sortByBrand(List<Category_Entity> category, String sortOrder) {
    return category.stream()
            .sorted("desc".equalsIgnoreCase(sortOrder)
                    ? Comparator.comparing(Category_Entity::getID_BRAND).reversed()
                    : Comparator.comparing(Category_Entity::getID_BRAND))
            .collect(Collectors.toList());
  }



  public boolean updateCategory(String id, String name,String id_parent,String id_brand,boolean is_product) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Category_Entity user = entityManager.find(Category_Entity.class, id);
      if (user == null) {
        return false;
      }
      if (name != null) user.setNAME_CATEGORY(name);
      if (id_parent != null) user.setID_PARENT(id_parent);
      if (id_brand != null) user.setID_BRAND(id_brand);
      user.setIS_BASE_PRODUCT(is_product);
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