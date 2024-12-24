package com.example.dkkp.controller;

import com.example.dkkp.model.*;
import com.example.dkkp.service.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestController {

    //        "bkl1@gmail.com","pass123"
//        "ca677198d7ebbc24736d79bdc4a493b0293f363d5e881985d190e2c626376dff"
    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();


            ProductBaseService productBaseService = new ProductBaseService(entityManager);
            Product_Attribute_Entity n = new Product_Attribute_Entity(1,"not clot",null);
            productBaseService.deleteProductAttribute(1);
//            BillService billService = new BillService(entityManager);
//            ImportService importService = new ImportService(entityManager);
//            Import_Detail_Entity import_Detail_Entity = new Import_Detail_Entity(null, 1, true, 1, null, 10, 200.0, 2000.0, "no");
//            Import_Detail_Entity import_Detail_Entity1 = new Import_Detail_Entity(null, 1, true, null, 4, 10, 200.0, 2000.0, "no");
//            List<Import_Detail_Entity> import_Detail_EntityList = new ArrayList<>();
//            import_Detail_EntityList.add(import_Detail_Entity1);
//            import_Detail_EntityList.add(import_Detail_Entity);
//            importService.registerNewImportDetail(import_Detail_EntityList);
//
//            Import_Entity import_entity = new Import_Entity(null, LocalDateTime.now(), "nope", true, null, null);
//            importService.registerNewImport(import_entity);
//
//            ProductFinalService productFinalService = new ProductFinalService(entityManager);
//
//            Product_Option_Values_Entity productOptionValues_Entity = new Product_Option_Values_Entity(null, 2, "50gb", 4);
//            productFinalService.createProductOptionValues(productOptionValues_Entity);
//
//            Product_Option_Entity product_option_entity = new Product_Option_Entity(null, "Dimension");
//            productFinalService.createProductOption(product_option_entity);
//
//
//            Product_Final_Entity product_final_entity = new Product_Final_Entity(null, 1, "Iphone 12 red", 12, "the red color", 200000.0, 7.0, "colo");
//            productFinalService.createProductFinal(product_final_entity);
//
//            Product_Attribute_Values_Entity productAttributeValues_Entity = new Product_Attribute_Values_Entity(null, 1, 1, "red");
//            ProductBaseService productBaseService = new ProductBaseService(entityManager);
//            productBaseService.createProductAttributeValues(productAttributeValues_Entity);
//
//            Product_Attribute_Entity product_attribute_entity = new Product_Attribute_Entity(null, "color", 1);
////            ProductBaseService productBaseService = new ProductBaseService(entityManager);
//            productBaseService.createProductAttribute(product_attribute_entity);
//
//            Product_Base_Entity base_entity = new Product_Base_Entity(null, "Iphone 12", null, LocalDateTime.now(), "Newest iphone", null, 1, 5);
////            ProductBaseService productBaseService = new ProductBaseService(entityManager);
//            productBaseService.createProductBase(base_entity);
//
//            Category_Entity category = new Category_Entity("smart phone");
//            CategoryService categoryService = new CategoryService(entityManager);
//            categoryService.createNewCategory(category);
//
//
////            BillService billService = new BillService(entityManager);
//            Bill_Detail_Entity billDetailEntity = new Bill_Detail_Entity(null, 1, 2, 2000.0, 4, true);
//            Bill_Detail_Entity billDetailEntity2 = new Bill_Detail_Entity(null, 1, 3, 2000.0, 4, true);
//            List<Bill_Detail_Entity> billDetailEntityList = new ArrayList<>();
//            billDetailEntityList.add(billDetailEntity);
//            billDetailEntityList.add(billDetailEntity2);
//            billService.registerNewBillDetail(billDetailEntityList);
//            billService.changeBillStatus(1, EnumType.Status_Bill.CANC);
//
//            Bill_Entity bill = new Bill_Entity(null, LocalDateTime.now(), "6aa068cea138aac9f92486e5aeb950bed5b6bdf79f00485076a103cd1f93b037", 2000.0, "deo co gi", EnumType.Status_Bill.PEN);
//            billService.registerNewBill(bill);
//
//            User_Entity user = new User_Entity("bb ostret 1", "Name user another"
//                    , "normal", "pass123", "0987483904", "dcm@gmail.com"
//            );
//            UserService userService = new UserService(entityManager);
//            userService.registerNewUser(user);
//
//            Brand_Entity brand_Entity = new Brand_Entity(null, "Samsung", "From samsung manufacturer");
//            BrandService brandService = new BrandService(entityManager);
//            brandService.createNewBrand(brand_Entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


}
