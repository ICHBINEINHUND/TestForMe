package com.example.dkkp.service;

import com.example.dkkp.dao.CategoryDao;
import com.example.dkkp.model.Category_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public CategoryService(EntityManager entityManager) {
        this.categoryDao = new CategoryDao(entityManager);
        this.entityManager = entityManagerFactory.createEntityManager();
    }


    public void createNewCategory(Category_Entity category) {
        // chạy được
        // cần add kiểm tra hợp lệ cho các thuộc tính
             categoryDao.createCategory(category);
    }

    public List<Category_Entity> getFilteredCategories(
            Category_Entity category,
            String sortField,
            String sortOrder
    ) {
        // chạy được
        // không cần thêm check
            if (reflectField.isPropertyNameMatched(Category_Entity.class, sortField) || sortField == null) {
                Integer id = category.getID_CATEGORY();
                String name = category.getNAME_CATEGORY();

                return categoryDao.getFilteredCategories(
                        id, name, sortField, sortOrder
                );
            }else{
                throw new RuntimeException("Error with sort field category");
            }

    }

    public void deleteCategory(Integer id) {
            categoryDao.deleteCategoryById(id);
    }


    public void updateCategory(Category_Entity category) {
        // add check
        // chạy được
        Integer id = category.getID_CATEGORY();
        String name = category.getNAME_CATEGORY();
         categoryDao.updateCategory(id, name);

    }
}