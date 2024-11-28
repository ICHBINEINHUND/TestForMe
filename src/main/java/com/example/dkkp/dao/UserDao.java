package com.example.dkkp.dao;

import com.example.dkkp.model.User_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public UserDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createUser(User_Entity user) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(user);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public List<User_Entity> getAllUsers() {
    String jpql = "SELECT u FROM User_Entity u";
    TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
    return query.getResultList();
  }

  public List<User_Entity> getUsersByName(String name) {
    String jpql = "SELECT u FROM User_Entity u WHERE u.NAME_USER = :name";
    TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
    query.setParameter("name", name);
    return query.getResultList();
  }

  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}