package com.example.dkkp.dao;

import com.example.dkkp.model.Email_Check_Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class EmailCheckDao {
  private final EntityManager entityManager;
  private static final EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
  }

  public EmailCheckDao() {
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public void createToken(Email_Check_Entity user) {
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

  public boolean checkUsersByID(String email) {
    String jpql = "SELECT COUNT(u) FROM Email_Check_Entity u WHERE u.EMAIL = :email AND u.DATE_END > :time";
    TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
    LocalDateTime time = LocalDateTime.now();
    query.setParameter("email", email);
    query.setParameter("time", time);
    Long count = query.getSingleResult();
    return count > 0;
  }




  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}