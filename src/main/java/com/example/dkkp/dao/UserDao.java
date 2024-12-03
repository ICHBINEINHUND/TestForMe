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

  public User_Entity getUsersByID(String id) {
    String jpql = "SELECT u FROM User_Entity u WHERE u.ID_USER = :id";
    TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }
  public User_Entity getUsersByMail(String mail) {
    String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :mail";
    TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
    query.setParameter("mail", mail);
    return query.getSingleResult();
  }
  public boolean isUserByMail(String mail) {
    String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :mail";
    TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
    query.setParameter("mail", mail);
    query.setMaxResults(1);
    return !query.getResultList().isEmpty();
  }

  public boolean loginValidate(String EMAIL_ACC, String PASSWORD_ACC) {
    String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :email AND u.PASSWORD_ACC = :password";
    TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
    query.setParameter("email", EMAIL_ACC);
    query.setParameter("password", PASSWORD_ACC);
    List<User_Entity> users = query.getResultList();
    return !users.isEmpty();
  }

  public boolean updateUser(String id, String email, String phone, String password, String role, String name) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      User_Entity user = entityManager.find(User_Entity.class, id);
      if (user == null) {
        return false;
      }
      if (email != null) user.setEMAIL_ACC(email);
      if (phone != null) user.setPHONE_ACC(phone);
      if (password != null) user.setPASSWORD_ACC(password);
      if (role != null) user.setROLE_ACC(role);
      if (name != null) user.setNAME_USER(name);
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

  public boolean changePasswordByEmail(String email, String newPassword) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();

      String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :email";
      TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
      query.setParameter("email", email);
      User_Entity user = query.getResultStream().findFirst().orElse(null);
      if (user == null) {
        return false;
      }

      user.setPASSWORD_ACC(newPassword);
      entityManager.merge(user);

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


  public static void shutdown() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}