package com.example.dkkp.controller.product.productOption;

import com.example.dkkp.controller.product.productOption.ProductOptionController;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Option_Entity;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.ProductFinalService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;

import static com.example.dkkp.controller.LoginController.*;


public class ProductOptionCreateController {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    
    @FXML
    private MFXTextField NAME_OPTION;
    
    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;


    ProductOptionController productOptionController;
    @FXML
    public void createProduct() {
        String name = (NAME_OPTION.getText().isEmpty()) ? null : NAME_OPTION.getText();
            transaction.begin();
            try {
                Product_Option_Entity productOptionEntity = new Product_Option_Entity(null, name);
                ProductFinalService productFinalService = new ProductFinalService(entityManager);
                productFinalService.createProductOption(productOptionEntity);
                transaction.commit();
                productOptionController.productController.setMainView("/com/example/dkkp/ProductOption/ProductOptionView.fxml", productOptionController);
            } catch (Exception e) {
                transaction.rollback();
            }
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            productOptionController.productController.setMainView("/com/example/dkkp/ProductOption/ProductOptionView.fxml", productOptionController);
        });

    }


    public void setProductOptionController(ProductOptionController productOptionController) {
        this.productOptionController = productOptionController;
    }
}