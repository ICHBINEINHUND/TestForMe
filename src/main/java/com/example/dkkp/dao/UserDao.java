package com.example.dkkp.dao;

import com.example.dkkp.model.User_Entity;
import com.example.dkkp.service.SecurityFunction;
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

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean createUser(User_Entity user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error creating user", e);
        }
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
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
        return query.getResultList().get(0);
    }

    public User_Entity getUsersByMail(String mail) {
        String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :mail";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        query.setParameter("mail", mail);
        List<User_Entity> result = query.getResultList();
        return result.getFirst();
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

    public void updateUser(String id, String add, String email, String phone, String role, String name) {
        User_Entity user = entityManager.find(User_Entity.class, id);
        if (user == null) {
            throw new RuntimeException("Can not find user");
        }
        if (email != null) user.setEMAIL_ACC(email);
        if (add != null) user.setADDRESS(add);
        if (phone != null) user.setPHONE_ACC(phone);
        if (role != null) user.setROLE_ACC(role);
        if (name != null) user.setNAME_USER(name);
        entityManager.merge(user);

    }

    public void changePasswordByEmail(String email, String newPassword) {
        String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :email";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        query.setParameter("email", email);
        User_Entity user = query.getResultStream().findFirst().orElse(null);
        if (user == null) {
            throw new RuntimeException("Could not find user with email " + email);
        }
        String newSalt = SecurityFunction.generateSalt();
        user.setSALT(newSalt);
        String newPass = SecurityFunction.hashString(newPassword + newSalt);
        user.setPASSWORD_ACC(newPass);
        entityManager.merge(user);

    }


    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}