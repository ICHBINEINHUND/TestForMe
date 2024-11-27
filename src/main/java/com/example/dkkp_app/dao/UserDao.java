package com.example.dkkp_app.dao;

import com.example.dkkp_app.model.User_Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

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

    public List<User_Entity> getAllUsers() {
        try {
            // Sử dụng JPQL để lấy tất cả đối tượng User_Entity
            String jpql = "SELECT u FROM User_Entity u"; // Truy vấn tất cả các bản ghi trong bảng
            Query query = entityManager.createQuery(jpql);
            return query.getResultList(); // Trả về danh sách các đối tượng User_Entity
        } catch (RuntimeException e) {
            // Log lỗi nếu cần và ném lại ngoại lệ
            throw e;
        }
    }
}