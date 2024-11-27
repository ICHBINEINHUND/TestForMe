package com.example.dkkp_app.dao;

import com.example.dkkp_app.model.User_Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class UserDao {

    private EntityManager entityManager;

    public UserDao() {
        this.entityManager = Persistence.createEntityManagerFactory("myJpaUnit").createEntityManager();
    }

    // Phương thức tạo và lưu đối tượng User_Entity
    public void createUser(User_Entity user) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(user);  // Lưu đối tượng vào cơ sở dữ liệu
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();  // Rollback nếu có lỗi xảy ra
            }
            throw e;  // Ném lại ngoại lệ để xử lý ở nơi khác
        }
    }
}