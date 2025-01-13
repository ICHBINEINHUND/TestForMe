package com.example.dkkp.controller.product.productAttributeValue;

import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Attribute_Entity;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.ProductBaseService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductAttributeValuesCreateController {
    
    @FXML
    private MFXTextField NAME_ATTRIBUTE;    
    @FXML
    private MFXFilterComboBox<Category_Entity> cateField;
    
    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;


    ProductAttributeValuesController productAttributeValuesController;
    @FXML
    public void createProduct() {
        String name = (NAME_ATTRIBUTE.getText().isEmpty()) ? null : NAME_ATTRIBUTE.getText();
        Integer categoryId = (cateField.getValue() != null) ? cateField.getValue().getID_CATEGORY() : null;
            transaction.begin();
            try {
                Product_Attribute_Entity productAttributeEntity = new Product_Attribute_Entity(null, name,categoryId);
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
                productBaseService.createProductAttribute(productAttributeEntity);
                transaction.commit();
                productAttributeValuesController.productController.setMainView("/com/example/dkkp/ProductAttribute/ProductAttributeView.fxml", productAttributeValuesController);
            } catch (Exception e) {
                transaction.rollback();
            }
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            productAttributeValuesController.productController.setMainView("/com/example/dkkp/ProductAttribute/ProductAttributeView.fxml", productAttributeValuesController);
        });

        CategoryService categoryService = new CategoryService(entityManager);
        Category_Entity categoryEntity = new Category_Entity();
        cateField.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null, null, null));
    }


    public void setProductAttributeValuesController(ProductAttributeValuesController productAttributeValuesController) {
        this.productAttributeValuesController = productAttributeValuesController;
    }
}