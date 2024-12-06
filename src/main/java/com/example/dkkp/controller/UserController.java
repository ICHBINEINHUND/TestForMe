package com.example.dkkp.controller;

import com.example.dkkp.model.*;
import com.example.dkkp.service.BrandService;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.ReportService;
import com.example.dkkp.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class UserController {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        CategoryService categoryService = new CategoryService();
        Category_Entity category_entity = new Category_Entity("CateIDcon2","CateName","CateID","TestBrand",true);
        Category_Entity category_entityToUPdate = new Category_Entity("CateIDcon2","new name","CateIDcon1","TestBrand",false);
        categoryService.deleteCategory("CateIDcon2");
//        List<Category_Entity> ct = categoryService.getFilteredCategories(category_entityToQuery,null,null,null,null);
//        for(Category_Entity c : ct) {
//            System.out.println(c.getID_CATEGORY());
//        }
    }

}
