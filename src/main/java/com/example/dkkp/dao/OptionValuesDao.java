package com.example.dkkp.dao;

import com.example.dkkp.model.Option_Values_Entity;
import com.example.dkkp.model.Option_Values_Entity;
import com.example.dkkp.model.Option_Values_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class OptionValuesDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public OptionValuesDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  public boolean createOptionValues(Option_Values_Entity optionValue) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(optionValue);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
        return false;
      }
      throw new RuntimeException("Error creating option values", e);
    }
  }
  public boolean existsOptionValueById(Integer id) {
    try {
      Option_Values_Entity optionValue = entityManager.find(Option_Values_Entity.class, id);
      return optionValue != null;
    } catch (RuntimeException e) {
      throw new RuntimeException("Error checking existence of Option Value", e);
    }
  }

  public boolean updateOptionValue(Integer id,
                               String value,
                               String parent_id,
                               String option_id) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Option_Values_Entity option_values_entity = entityManager.find(Option_Values_Entity.class, id);
      if (option_values_entity == null) {
        return false;
      }
      if (value != null) option_values_entity.setVALUE(value);
      if (parent_id != null) option_values_entity.setID_PARENT(parent_id);
      if (option_id != null) option_values_entity.setID_OPTION(option_id);
      entityManager.merge(option_values_entity);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<Option_Values_Entity> getFilteredBills( Integer id, String idOptionOption,String value, String idParent, String sortField, String sortOrder, Integer offset, Integer setOff) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Option_Values_Entity> query = cb.createQuery(Option_Values_Entity.class);
    Root<Option_Values_Entity> root = query.from(Option_Values_Entity.class);

    Predicate conditions = cb.conjunction();

    if (id != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_VALUE"), id));
    }
    if (idOptionOption != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_OPTION"), idOptionOption));
    }
    if (value != null) {
      conditions = cb.and(conditions, cb.equal(root.get("VALUE"), value));
    }
    if (idParent != null) {
      conditions = cb.and(conditions, cb.equal(root.get("ID_PARENT"), idParent));
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
    TypedQuery<Option_Values_Entity> typedQuery = entityManager.createQuery(query);
    if(offset !=null) typedQuery.setFirstResult(offset);
    if(setOff !=null) typedQuery.setMaxResults(setOff);
    return typedQuery.getResultList();
  }

  public boolean deleteOptionValues(Integer id, String idOption, String idParent) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      StringBuilder queryStr = new StringBuilder("SELECT po FROM Option_Values_Entity po WHERE 1=1");

      if (id != null) {
        queryStr.append(" AND po.id = :id");
      }
      if (idOption != null) {
        queryStr.append(" AND po.ID_OPTION = :idOption");
      }
      if (idParent != null) {
        queryStr.append(" AND po.ID_PARENT = :idParent");
      }

      var query = entityManager.createQuery(queryStr.toString(), Option_Values_Entity.class);

      if (id != null) {
        query.setParameter("id", id);
      }
      if (idOption != null) {
        query.setParameter("idOption", idOption);
      }
      if (idParent != null) {
        query.setParameter("idParent", idParent);
      }

      List<Option_Values_Entity> optionValues = query.getResultList();

      if (!optionValues.isEmpty()) {
        for (Option_Values_Entity optionValue : optionValues) {
          try {
            entityManager.remove(optionValue);
          } catch (RuntimeException e) {
            transaction.rollback();
            throw new RuntimeException("Error occurred while deleting Option Value", e);
          }
        }
        transaction.commit();
        return true;
      }

      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
        throw new RuntimeException("Error occurred while deleting Option Value", e);
      }
      throw e;
    }

    return false;
  }



  public boolean updateOptionValues(Integer id, String value, String idParent, String idOption) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Option_Values_Entity optionValue = entityManager.find(Option_Values_Entity.class, id);
      if (optionValue == null) {
        return false;
      }
      if (value != null) optionValue.setVALUE(value);
      if (idParent != null) optionValue.setID_PARENT(idParent);
      if (idOption != null) optionValue.setID_OPTION(idOption);
      entityManager.merge(optionValue);
      transaction.commit();
      return true;
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw new RuntimeException("Error occurred while updating Option Value", e);
    }
  }

  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}
