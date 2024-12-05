package com.example.dkkp.controller;

import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Report_Bug;
import com.example.dkkp.model.User_Entity;
import com.example.dkkp.service.BrandService;
import com.example.dkkp.service.ReportService;
import com.example.dkkp.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class UserController {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        BrandService brandService = new BrandService();
//        Brand_Entity brantity= new Brand_Entity("TestBrand","TenBrand","chonguDes1");
        Brand_Entity brandToUpdate = new Brand_Entity("TestBrand","TenBrandmoi","moi");
        brandService.updateBrand(brandToUpdate);
        Brand_Entity brandEntity = new Brand_Entity("TestBrand",null,null);
        brandService.deleteBrand("TestBrand");
        List<Brand_Entity> brandEntities= brandService.getBrandBy(brandEntity,"NAME_BRAND","desc");
        for (Brand_Entity brandEntity1 : brandEntities) {
            System.out.println(brandEntity1.getID_BRAND());
        }

    }
}
