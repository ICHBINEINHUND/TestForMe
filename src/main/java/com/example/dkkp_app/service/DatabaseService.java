package com.example.dkkp_app.service;

import com.example.dkkp_app.model.User_Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class DatabaseService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpaUnit");

    // Lấy EntityManager từ EntityManagerFactory
    private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Thêm mới một User
    public static void addEntity(User_Entity entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);  // Lưu đối tượng entity vào cơ sở dữ liệu
        em.getTransaction().commit();
        em.close();
    }

    // Lấy tất cả User
    public static List<User_Entity> getAllEntities() {
        EntityManager em = getEntityManager();
        // Sử dụng đúng tên bảng được ánh xạ từ Entity
        TypedQuery<User_Entity> query = em.createQuery("SELECT e FROM User_Entity e", User_Entity.class);
        List<User_Entity> entities = query.getResultList();
        em.close();
        return entities;
    }

    // Lấy User theo ID
    public static User_Entity getEntityById(Long id) {
        EntityManager em = getEntityManager();
        User_Entity entity = em.find(User_Entity.class, id);
        em.close();
        return entity;
    }
}
