package com.example.dkkp.controller.product.productAttribute;

import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Attribute_Entity;
import com.example.dkkp.service.Validator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class ProductAttributeFilterController {

    @FXML
    private MFXTextField ID_CATEGORY;
    @FXML
    private MFXTextField NAME_CATEGORY;

    @FXML
    private MFXButton back;
    @FXML
    private MFXButton applyButton;
    private Stage popupStage;
    ProductAttributeController productAttributeController;
    @FXML
    public void createFilter() {
        Integer id = ID_CATEGORY.getText().trim().isEmpty() ? null : Integer.parseInt(ID_CATEGORY.getText());
        String name = NAME_CATEGORY.getText().trim().isEmpty() ? null : NAME_CATEGORY.getText();

        productAttributeController.productAttributeEntity = new Product_Attribute_Entity();
        productAttributeController.setPage(1);
        productAttributeController.productController.setMainView("/com/example/dkkp/Category/ProductCategoryView.fxml",productAttributeController);
        productAttributeController.closePopup(popupStage);
    }


    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }
    @FXML
    public void initialize() {
        setTextFormatter();
        applyButton.setOnAction(event -> createFilter());
        back.setOnMouseClicked(event -> {
            productAttributeController.closePopup(popupStage);
        });
    }
    private void setTextFormatter(){
        Validator validator1 = new Validator();
        ID_CATEGORY.delegateSetTextFormatter(validator1.formatterInteger);
    }
    public void setProductCategoryController(ProductAttributeController productAttributeController) {
        this.productAttributeController = productAttributeController;
    }
}