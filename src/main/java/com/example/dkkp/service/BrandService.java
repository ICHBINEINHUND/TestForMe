package com.example.dkkp.service;

import com.example.dkkp.dao.BrandDao;
import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.Category_Entity;
import jakarta.persistence.EntityManager;


import java.util.List;

public class BrandService {
    private final BrandDao brandDao;

    public BrandService(EntityManager entityManager) {
        this.brandDao = new BrandDao(entityManager);
    }


    public void createNewBrand(Brand_Entity brand) {
        // chạy được
        //add check thông tin trước khi tạo brand mới
        brandDao.createBrand(brand);
    }

    public List<Brand_Entity> getFilteredBrand(
            Brand_Entity brand,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offSet
    ) {
        // chạy được
        // không cần thêm check

        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();

        return brandDao.getFilteredBrand(
                id, name, sortField, sortOrder,setOff, offSet
        );

    }

    public Integer getCountFilteredBrand(
            Brand_Entity brand
    ) {
        // chạy được
        // không cần thêm check
        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();
        return brandDao.getFilteredBrandCount(
                id, name
        );

    }

    public List<Brand_Entity> getBrandBy(
            Brand_Entity brand,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offSet
    ) {
        // chạy được
        // không cần kiểm tra sự hợp lệ của các tham số truyền vào khác như userId,...
        Integer ID_BRAND = brand.getID_BRAND();
        String NAME_BRAND = brand.getNAME_BRAND();
        return brandDao.getFilteredBrand(ID_BRAND, NAME_BRAND, sortField, sortOrder,setOff,offSet);
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