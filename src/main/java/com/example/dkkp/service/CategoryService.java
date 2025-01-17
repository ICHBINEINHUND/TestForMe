package com.example.dkkp.service;

import com.example.dkkp.dao.CategoryDao;
import com.example.dkkp.model.Category_Entity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;


    public CategoryService(EntityManager entityManager) {
        this.categoryDao = new CategoryDao(entityManager);
    }


    public void createNewCategory(Category_Entity category) {
        // chạy được
        // cần add kiểm tra hợp lệ cho các thuộc tính
             categoryDao.createCategory(category);
    }

    public List<Category_Entity> getFilteredCategories(
            Category_Entity category,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offSet
    ) {
        // chạy được
        // không cần thêm check
                Integer id = category.getID_CATEGORY();
                String name = category.getNAME_CATEGORY();

                return categoryDao.getFilteredCategories(
                        id, name, sortField, sortOrder,setOff,offSet
                );
    }

    public Integer getFilteredCategoriesCount(
            Category_Entity category
    ) {
        // chạy được
        // không cần thêm check
                Integer id = category.getID_CATEGORY();
                String name = category.getNAME_CATEGORY();
                return categoryDao.getFilteredCategoriesCount(id, name );
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