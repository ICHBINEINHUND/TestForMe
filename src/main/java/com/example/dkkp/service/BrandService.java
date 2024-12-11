package com.example.dkkp.service;

import com.example.dkkp.dao.BrandDao;
import com.example.dkkp.model.Brand_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class BrandService {
    private final BrandDao brandDao;
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public BrandService() {
        this.brandDao = new BrandDao();
        this.entityManager = entityManagerFactory.createEntityManager();
    }


    public boolean createNewBrand(Brand_Entity brand) {
        // chạy được
        //add check thông tin trước khi tạo brand mới
            String ID_BRAND = brand.getID_BRAND();
            String NAME_BRAND = brand.getNAME_BRAND();
            String DETAIl = brand.getDETAIL();
            return brandDao.createBrand(brand);
    }

    public List<Brand_Entity> getBrandBy(
            Brand_Entity brand,
            String sortField,
            String sortOrder
    ) {
        // chạy được
        // không cần kiểm tra sự hợp lệ của các tham số truyền vào khác như userId,...
        try {
            if (reflectField.isPropertyNameMatched(brand, sortField) || sortField == null) {
                String ID_BRAND = brand.getID_BRAND();
                String NAME_BRAND = brand.getNAME_BRAND();
                return brandDao.getFilteredBrand(ID_BRAND, NAME_BRAND, sortField, sortOrder);
            }
            return null;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get brand with filter", e);
        }
    }

    public boolean deleteBrand(String id) {
        // chạy được
        // không cần add check
            if (id != null) {
                return brandDao.deleteBrandById(id);
            }else{
                throw new RuntimeException("id is null");
            }
    }


    public boolean updateBrand(Brand_Entity brand) {
        // add check
        // chạy được
        // không cần add check
            String id = brand.getID_BRAND();
            String name = brand.getNAME_BRAND();
            String detail = brand.getDETAIL();
            if (id == null || (name == null && detail == null)) {
                throw new RuntimeException("id or detail is null");
            }
            return brandDao.updateBrand(id, name, detail);
    }
}