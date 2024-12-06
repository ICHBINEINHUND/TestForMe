package com.example.dkkp.service;

import com.example.dkkp.dao.BrandDao;
import com.example.dkkp.model.Brand_Entity;

import java.util.List;

public class BrandService {
    private final BrandDao brandDao;

    public BrandService() {
        this.brandDao = new BrandDao();
    }


    public boolean createNewBrand(Brand_Entity brand) {
        // chạy được
        //add check thông tin trước khi tạo brand mới
        try {
            String ID_BRAND = brand.getID_BRAND();
            String NAME_BRAND = brand.getNAME_BRAND();
            String DETAIl = brand.getDETAIL();
            return brandDao.createBrand(brand);
        } catch (
                RuntimeException e) {
            throw e;
        }
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
        } catch (
                RuntimeException e) {
            throw e;
        }
    }

    public boolean deleteBrand(String id) {
        // chạy được
        // không cần add check
        try {
            if (id != null) {
                return brandDao.deleteBrandById(id);
            }
            return false;
        } catch (
                RuntimeException e) {
            throw e;
        }
    }


    public boolean updateBrand(Brand_Entity brand) {
        // add check
        // chạy được
        // không cần add check
        try {
            String id = brand.getID_BRAND();
            String name = brand.getNAME_BRAND();
            String detail = brand.getDETAIL();
            if (id == null || (name == null && detail == null)) {

                return false;
            }
            return brandDao.updateBrand(id, name, detail);
        } catch (
                RuntimeException e) {
            throw e;
        }
    }
}