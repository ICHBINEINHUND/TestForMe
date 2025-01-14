package com.example.dkkp.controller.product.productOptionValue;

import com.example.dkkp.model.Product_Option_Entity;
import com.example.dkkp.service.ProductFinalService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductOptionValuesCreateController {
    
    @FXML
    private MFXTextField NAME_OPTION;
    
    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;


    ProductOptionValuesController productOptionValuesController;
    @FXML
    public void createProduct() {
        String name = (NAME_OPTION.getText().isEmpty()) ? null : NAME_OPTION.getText();
            transaction.begin();
            try {
                Product_Option_Entity productOptionEntity = new Product_Option_Entity(null, name);
                ProductFinalService productFinalService = new ProductFinalService(entityManager);
                productFinalService.createProductOption(productOptionEntity);
                transaction.commit();
                productOptionValuesController.productController.setMainView("/com/example/dkkp/ProductOption/ProductOptionView.fxml", productOptionValuesController);
            } catch (Exception e) {
                transaction.rollback();
            }
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            productOptionValuesController.productController.setMainView("/com/example/dkkp/ProductOptionValue/ProductOptionValueView.fxml", productOptionValuesController);
        });

    }


    public void setProductOptionValuesController(ProductOptionValuesController productOptionValuesController) {
        this.productOptionValuesController = productOptionValuesController;
    }
}