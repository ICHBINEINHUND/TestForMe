package com.example.dkkp.controller.product.productAttributeValue;

import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Attribute_Values_Entity;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.Validator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import static com.example.dkkp.controller.LoginController.entityManager;


public class ProductAttributeValuesFilterController {

    @FXML
    private MFXTextField ID_ATTRIBUTE;
    @FXML
    private MFXTextField NAME_ATTRIBUTE;
    @FXML
    private MFXFilterComboBox<Category_Entity> cateField;

    @FXML
    private MFXButton back;
    @FXML
    private MFXButton applyButton;
    private Stage popupStage;
    ProductAttributeValuesController productAttributeValuesController;
    @FXML
    public void createFilter() {
        Integer id = ID_ATTRIBUTE.getText().trim().isEmpty() ? null : Integer.parseInt(ID_ATTRIBUTE.getText());
        String name = NAME_ATTRIBUTE.getText().trim().isEmpty() ? null : NAME_ATTRIBUTE.getText();
        Integer categoryId = (cateField.getValue() != null) ? cateField.getValue().getID_CATEGORY() : null;

        productAttributeValuesController.productAttributeValuesEntity = new Product_Attribute_Values_Entity(id,null, null, null);
        productAttributeValuesController.setPage(1);
        productAttributeValuesController.productController.setMainView("/com/example/dkkp/ProductAttribute/ProductAttributeView.fxml", productAttributeValuesController);
        productAttributeValuesController.closePopup(popupStage);
    }


    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }
    @FXML
    public void initialize() {
        setTextFormatter();
        applyButton.setOnAction(event -> createFilter());
        back.setOnMouseClicked(event -> {
            productAttributeValuesController.closePopup(popupStage);
        });
        CategoryService categoryService = new CategoryService(entityManager);
        Category_Entity categoryEntity = new Category_Entity();
        cateField.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null, null, null));

    }
    private void setTextFormatter(){
        Validator validator1 = new Validator();
        ID_ATTRIBUTE.delegateSetTextFormatter(validator1.formatterInteger);
    }
    public void setProductAttributeValuesController(ProductAttributeValuesController productAttributeValuesController) {
        this.productAttributeValuesController = productAttributeValuesController;
    }
}