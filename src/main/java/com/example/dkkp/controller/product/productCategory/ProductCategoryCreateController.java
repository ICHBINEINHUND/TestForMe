package com.example.dkkp.controller.product.productCategory;

import com.example.dkkp.controller.product.productCategory.ProductCategoryController;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.service.CategoryService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;

import static com.example.dkkp.controller.LoginController.*;


public class ProductCategoryCreateController {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    @FXML
    private MFXTextField NAME_CATEGORY;
    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;
   

    ProductCategoryController productCategoryController;
    @FXML
    public void createProduct() {
        String name = (NAME_CATEGORY.getText().isEmpty()) ? null : NAME_CATEGORY.getText();
            transaction.begin();
            try {

                Category_Entity categoryEntity = new Category_Entity(null, name);
                CategoryService categoryService = new CategoryService(entityManager);
                categoryService.createNewCategory(categoryEntity);
                transaction.commit();
                productCategoryController.productController.setMainView("/com/example/dkkp/Category/ProductCategoryView.fxml", productCategoryController);
            } catch (Exception e) {
                transaction.rollback();
            }
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            productCategoryController.productController.setMainView("/com/example/dkkp/Category/ProductCategoryView.fxml", productCategoryController);
        });
    }


    public void setProductCategoryController(ProductCategoryController productCategoryController) {
        this.productCategoryController = productCategoryController;
    }
}