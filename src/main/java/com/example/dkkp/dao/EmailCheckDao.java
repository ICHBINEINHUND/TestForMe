package com.example.dkkp.dao;

import com.example.dkkp.model.Email_Check_Entity;
import jakarta.persistence.*;
import java.util.List;

public class EmailCheckDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public EmailCheckDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void createToken(Email_Check_Entity email) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(email);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw new RuntimeException("Error creating token", e);
    }
  }

  public void deleteTokensByEmail(String emailAddress) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      String jpql = "SELECT e FROM Email_Check_Entity e WHERE e.EMAIL = :emailAddress";
      TypedQuery<Email_Check_Entity> query = entityManager.createQuery(jpql, Email_Check_Entity.class);
      query.setParameter("emailAddress", emailAddress);
      List<Email_Check_Entity> tokensToDelete = query.getResultList();
      if (!tokensToDelete.isEmpty()) {
        for (Email_Check_Entity token : tokensToDelete) {
          entityManager.remove(token);
        }
        transaction.commit();
        return;
      }
      transaction.rollback();
    } catch (RuntimeException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw new RuntimeException("Error deleting token", e);
    }
  }

  public boolean checkToken(String email, String token) {
    String jpql = "SELECT u FROM Email_Check_Entity u WHERE u.EMAIL = :email and u.TOKEN = :token";
    TypedQuery<Email_Check_Entity> query = entityManager.createQuery(jpql, Email_Check_Entity.class);
    query.setParameter("email", email);
    query.setParameter("token", token);
    return query.getResultStream().findFirst().isPresent();
  }

  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}