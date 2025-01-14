package com.example.dkkp.controller.product.productOptionValue;

import com.example.dkkp.model.Product_Option_Values_Entity;
import com.example.dkkp.service.ProductFinalService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductOptionValuesUpdateController {
    private Product_Option_Values_Entity productOptionValuesEntity;
    private ProductOptionValuesController productOptionValuesController;

    @FXML
    private MFXTextField ID_OPTION;
    @FXML
    private MFXTextField NAME_OPTION;
    @FXML
    private MFXButton updateBtn;
    @FXML
    private MFXButton backBtn;
    private Stage popupStage;

    @FXML
    public void initialize() {
        pushEntity();
        updateBtn.setOnAction(event -> updateCategory());
        backBtn.setOnMouseClicked(event -> productOptionValuesController.closePopup(popupStage));


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
//                productOptionValuesEntity.setNAME_OPTION(NAME_OPTION.getText());
                ProductFinalService productFinalService = new ProductFinalService(entityManager);
                productFinalService.updateProductOptionValues(productOptionValuesEntity);

                transaction.commit();
                productOptionValuesController.setMainView("/com/example/dkkp/ProductOption/ProductOptionView.fxml", productOptionValuesController);
                productOptionValuesController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
    public void pushEntity() {
        if (productOptionValuesEntity != null) {
            ID_OPTION.setText(productOptionValuesEntity.getID_OPTION().toString());
//            NAME_OPTION.setText(productOptionValuesEntity.getNAME_OPTION());
        }
    }
    public void setProductOptionValuesController(ProductOptionValuesController productOptionValuesController) {
        this.productOptionValuesController = productOptionValuesController;

    }


    public void setEntity(Product_Option_Values_Entity productOptionValuesEntity) {
        this.productOptionValuesEntity = productOptionValuesEntity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}