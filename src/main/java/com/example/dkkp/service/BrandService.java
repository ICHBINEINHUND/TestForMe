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
        // cần add check kiểm tra sortOrder phải bằng tên của các cột trong bảng thuộc tính
        // còn sort field cần phải bằng asc hoặc desc
        // recommed dùng equalsIgnoreCase để loại bỏ sự khác biệt giữa chữ hoa và thường
        // không cần kiểm tra sự hợp lệ của các tham số truyền vào khác như userId,...
        String ID_BRAND = brand.getID_BRAND();
        String NAME_BRAND = brand.getNAME_BRAND();
        return brandDao.getFilteredBrand(ID_BRAND, NAME_BRAND, sortField, sortOrder);
    }

    public boolean deleteBrand(String id) {
        // chạy được
        // không cần add check
        if (id != null) {
            return brandDao.deleteBrandById(id);
        }
        return false;
    }


    public boolean updateBrand(Brand_Entity brand) {
        // add check
        // chạy được
        // không cần add check
        String id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();
        String detail = brand.getDETAIL();
        if (id == null || (name == null && detail == null)) {

            return false;
        }
        return brandDao.updateBrand(id, name, detail);
    }
}