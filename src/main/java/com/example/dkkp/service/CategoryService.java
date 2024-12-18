package com.example.dkkp.service;

import com.example.dkkp.dao.CategoryDao;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Category_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.dkkp.model.EnumType.Bug_Type.UI;

public class CategoryService {
    private final CategoryDao categoryDao;
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public CategoryService() {
        this.categoryDao = new CategoryDao();
        this.entityManager = entityManagerFactory.createEntityManager();
    }


    public void createNewCategory(Category_Entity category) {
        // chạy được
        // cần add kiểm tra hợp lệ cho các thuộc tính
            String ID_CATEGORY = category.getID_CATEGORY();
            String NAME_CATEGORY = category.getNAME_CATEGORY();
            String ID_PARENT = category.getID_PARENT();
            String ID_BRAND = category.getID_BRAND();
            boolean IS_BASE_PRODUCT = true;
            Category_Entity categoryC = new Category_Entity(ID_CATEGORY, NAME_CATEGORY, ID_PARENT, ID_BRAND, IS_BASE_PRODUCT);
            categoryDao.createCategory(category);
    }

    public List<Category_Entity> getFilteredCategories(
            Category_Entity category,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff
    ) {
        // chạy được
        // không cần thêm check
            if (reflectField.isPropertyNameMatched(category, sortField) || sortField == null) {
                String id = category.getID_CATEGORY();
                String name = category.getNAME_CATEGORY();
                String idParent = category.getID_PARENT();
                String idBrand = category.getID_BRAND();
                Boolean isBaseProduct = category.getIS_BASE_PRODUCT();
                return categoryDao.getFilteredCategories(
                        id, name, idParent, idBrand, isBaseProduct, sortField, sortOrder, offset, setOff
                );
            }else{
                throw new RuntimeException("Error with sort field category");
            }

    }

    public boolean deleteCategory(String id) {
        if (id != null) {
            categoryDao.deleteCategoryById(id);
            return true;
        }else{
            throw new RuntimeException("Id category is null");
        }
    }


    public boolean updateCategory(Category_Entity category) {
        // add check
        // chạy được
            String id = category.getID_CATEGORY();
            String name = category.getNAME_CATEGORY();
            String id_parent = category.getID_PARENT();
            String id_brand = category.getID_BRAND();
            boolean is_product = category.getIS_BASE_PRODUCT();
            if (id != null) {
                return categoryDao.updateCategory(id, name, id_parent, id_brand, is_product);
            }else{
                throw new RuntimeException("Id category is null");
            }
    }
}