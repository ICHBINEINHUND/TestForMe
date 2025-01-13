package com.example.dkkp.controller.product.productCategory;

import com.example.dkkp.controller.product.productCategory.ProductCategoryController;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.service.CategoryService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductCategoryUpdateController {
    private Category_Entity categoryEntity;
    private ProductCategoryController productCategoryController;

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
        backBtn.setOnMouseClicked(event -> productCategoryController.closePopup(popupStage));
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
                categoryEntity.setNAME_CATEGORY(NAME_CATEGORY.getText());
                CategoryService categoryService = new CategoryService(entityManager);
                categoryService.updateCategory(categoryEntity);

                transaction.commit();
                productCategoryController.setMainView("/com/example/dkkp/Category/ProductCategoryView.fxml", productCategoryController);
                productCategoryController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
    public void pushEntity() {
        if (categoryEntity != null) {
            ID_CATEGORY.setText(categoryEntity.getID_CATEGORY().toString());
            NAME_CATEGORY.setText(categoryEntity.getNAME_CATEGORY());
        }
    }
    public void setProductCategoryController(ProductCategoryController productCategoryController) {
        this.productCategoryController = productCategoryController;

    }


    public void setEntity(Category_Entity category_Entity) {
        this.categoryEntity = category_Entity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}