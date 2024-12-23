package com.example.dkkp.service;

import com.example.dkkp.dao.BrandDao;
import com.example.dkkp.model.Brand_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class BrandService {
    private final BrandDao brandDao;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public BrandService(EntityManager entityManager) {
        this.brandDao = new BrandDao(entityManager);
    }


    public void createNewBrand(Brand_Entity brand) {
        // chạy được
        //add check thông tin trước khi tạo brand mới
         brandDao.createBrand(brand);
    }

    public List<Brand_Entity> getBrandBy(
            Brand_Entity brand,
            String sortField,
            String sortOrder
    ) {
        // chạy được
        // không cần kiểm tra sự hợp lệ của các tham số truyền vào khác như userId,...
            if (reflectField.isPropertyNameMatched(Brand_Entity.class, sortField) || sortField == null) {
                Integer ID_BRAND = brand.getID_BRAND();
                String NAME_BRAND = brand.getNAME_BRAND();
                return brandDao.getFilteredBrand(ID_BRAND, NAME_BRAND, sortField, sortOrder);
            }
            return null;
    }

    public void deleteBrand(Integer id) {
        // chạy được
        // không cần add check
         brandDao.deleteBrandById(id);
    }


    public void updateBrand(Brand_Entity brand) {
        // add check
        // chạy được
        // không cần add check
        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();
        String detail = brand.getDETAIL();
         brandDao.updateBrand(id, name, detail);
    }
}