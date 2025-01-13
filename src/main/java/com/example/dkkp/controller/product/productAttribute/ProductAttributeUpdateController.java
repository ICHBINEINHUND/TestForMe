package com.example.dkkp.controller.product.productAttribute;

import com.example.dkkp.model.Product_Attribute_Entity;
import com.example.dkkp.model.Product_Attribute_Entity;
import com.example.dkkp.service.ProductBaseService;
import io.github.palexdev.materialfx.controls.MFXButton;
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
    private MFXTextField ID_CATEGORY;
    @FXML
    private MFXTextField NAME_CATEGORY;

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
                productAttributeEntity.setNAME_CATEGORY(NAME_CATEGORY.getText());
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
                productBaseService.updateProductAttribute(productAttributeEntity);

                transaction.commit();
                productAttributeController.setMainView("/com/example/dkkp/Category/ProductCategoryView.fxml", productAttributeController);
                productAttributeController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
    public void pushEntity() {
        if (productAttributeEntity != null) {
            ID_CATEGORY.setText(productAttributeEntity.getID_CATEGORY().toString());
            NAME_CATEGORY.setText(productAttributeEntity.getNAME_CATEGORY());
        }
    }
    public void setProductCategoryController(ProductAttributeController productAttributeController) {
        this.productAttributeController = productAttributeController;

    }


    public void setEntity(Product_Attribute_Entity productAttributeEntity) {
        this.productAttributeEntity = productAttributeEntity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}