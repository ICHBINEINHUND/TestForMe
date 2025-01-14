package com.example.dkkp.controller.product.productAttribute;

import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Attribute_Entity;
import com.example.dkkp.model.Product_Attribute_Entity;
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


public class ProductAttributeUpdateController {
    private Product_Attribute_Entity productAttributeEntity;
    private ProductAttributeController productAttributeController;

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
        backBtn.setOnMouseClicked(event -> productAttributeController.closePopup(popupStage));


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
                    productAttributeEntity.setID_CATEGORY(cateField.getSelectionModel().getSelectedItem().getID_CATEGORY());
                }
                productAttributeEntity.setNAME_ATTRIBUTE(NAME_ATTRIBUTE.getText());
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
                productBaseService.updateProductAttribute(productAttributeEntity);

                transaction.commit();
                productAttributeController.setMainView("/com/example/dkkp/ProductAttribute/ProductAttributeView.fxml", productAttributeController);
                productAttributeController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
    public void pushEntity() {
        if (productAttributeEntity != null) {
            ID_ATTRIBUTE.setText(productAttributeEntity.getID_CATEGORY().toString());
            NAME_ATTRIBUTE.setText(productAttributeEntity.getNAME_ATTRIBUTE());

            CategoryService categoryService = new CategoryService(entityManager);
            Category_Entity categoryEntityDefault = categoryService.getFilteredCategories(new Category_Entity(productAttributeEntity.getID_CATEGORY(), null), null, null, null, null).getFirst();
            cateField.setText(categoryEntityDefault.toString());
            Category_Entity categoryEntity = new Category_Entity();
            cateField.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null, null, null));
        }
    }
    public void setProductAttributeController(ProductAttributeController productAttributeController) {
        this.productAttributeController = productAttributeController;

    }


    public void setEntity(Product_Attribute_Entity productAttributeEntity) {
        this.productAttributeEntity = productAttributeEntity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}