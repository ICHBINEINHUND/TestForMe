package com.example.dkkp.controller.product.productAttributeValue;

import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Attribute_Values_Entity;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.ProductBaseService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductAttributeValuesUpdateController {
    private Product_Attribute_Values_Entity productAttributeValuesEntity;
    private ProductAttributeValuesController productAttributeValuesController;

    @FXML
    private MFXTextField ID_ATTRIBUTE;
    @FXML
    private MFXTextField NAME_ATTRIBUTE;
    @FXML
    private MFXFilterComboBox<Category_Entity> cateField;

    @FXML
    private MFXButton updateBtn;
    @FXML
    private MFXButton backBtn;
    private Stage popupStage;

    @FXML
    public void initialize() {
        pushEntity();
        updateBtn.setOnAction(event -> updateCategory());
        backBtn.setOnMouseClicked(event -> productAttributeValuesController.closePopup(popupStage));


    }

    private void updateCategory() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to update this item?");
        alert.setContentText("This action cannot be undone.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            try {
                transaction.begin();
                if (cateField.getSelectionModel().getSelectedItem() != null) {
//                    productAttributeValuesEntity.get(cateField.getSelectionModel().getSelectedItem().getID_CATEGORY());
                }
                productAttributeValuesEntity.setNAME_ATTRIBUTE(NAME_ATTRIBUTE.getText());
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
//                productBaseService.updateProductAttribute(productAttributeValuesEntity);

                transaction.commit();
                productAttributeValuesController.setMainView("/com/example/dkkp/ProductAttribute/ProductAttributeView.fxml", productAttributeValuesController);
                productAttributeValuesController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
    public void pushEntity() {
        if (productAttributeValuesEntity != null) {
            ID_ATTRIBUTE.setText(productAttributeValuesEntity.getID().toString());
            NAME_ATTRIBUTE.setText(productAttributeValuesEntity.getNAME_ATTRIBUTE());

            CategoryService categoryService = new CategoryService(entityManager);
//            Category_Entity categoryEntityDefault = categoryService.getFilteredCategories(new Category_Entity(productAttributeValuesEntity.getID_CATEGORY(), null), null, null, null, null).getFirst();
            Category_Entity categoryEntity = new Category_Entity();
            cateField.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null, null, null));
//            cateField.setText(categoryEntityDefault.toString());
        }
    }
    public void setProductAttributeValuesController(ProductAttributeValuesController productAttributeValuesController) {
        this.productAttributeValuesController = productAttributeValuesController;

    }


    public void setEntity(Product_Attribute_Values_Entity productAttributeValuesEntity) {
        this.productAttributeValuesEntity = productAttributeValuesEntity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}