package com.example.dkkp.controller.impozt.importDetail;

import com.example.dkkp.controller.impozt.importGeneral.ImportGeneralCreateController;
import com.example.dkkp.model.*;
import com.example.dkkp.service.ProductBaseService;
import com.example.dkkp.service.ProductFinalService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ImportDetailCreateController {

    @FXML
    private MFXTextField QUANTITY;
    @FXML
    private MFXTextField UNIT_PRICE;
    @FXML
    private MFXTextField TOTAL_PRICE;
    @FXML
    private MFXTextField DESCRIPTION;
    @FXML
    private MFXFilterComboBox<Product_Final_Entity> finalProductCombobox;
    @FXML
    private MFXFilterComboBox<Product_Base_Entity> baseProductCombobox;

    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;


    ImportGeneralCreateController importGeneralCreateController;
    private Stage popupStage;

    @FXML
    public void createProduct() {
        String des = (DESCRIPTION.getText().isEmpty()) ? null : DESCRIPTION.getText();
        Integer quantity = (QUANTITY.getText().isEmpty()) ? null :Integer.parseInt(QUANTITY.getText()) ;
        Double uPrice = (UNIT_PRICE.getText().isEmpty()) ? null : Double.parseDouble(UNIT_PRICE.getText()) ;
        Double pPrice = (TOTAL_PRICE.getText().isEmpty()) ? null : Double.parseDouble(TOTAL_PRICE.getText());
        Integer finalProductId = (finalProductCombobox.getValue() != null) ? finalProductCombobox.getValue().getID_SP() : null;
        Integer baseProductId = (baseProductCombobox.getValue() != null) ? baseProductCombobox.getValue().getID_BASE_PRODUCT() : null;

        if (uPrice != null && quantity != null && pPrice != null) {
            double calculatedTotal = uPrice * quantity;
            if (calculatedTotal != pPrice) { // Dùng sai số nhỏ để so sánh số thực
                showAlert("Error", "The total price does not match the unit price multiplied by quantity.");
                return;
            }
        } else if (uPrice == null && pPrice == null) {
            showAlert("Error", "Total price and unit price cannot be the same empty .");
            return;
        } else if (uPrice != null && quantity != null) {
            pPrice = uPrice * quantity;

        }else if(pPrice != null && quantity != null){
            uPrice = (double) pPrice / quantity;
        }else{
            showAlert("Error", "Total price and unit price and quantitt cannot be the same empty.");
            return;
        }
        Import_Detail_Entity import_Detail_Entity = new Import_Detail_Entity();
        if(des != null) import_Detail_Entity.setDESCRIPTION(des);
        if(finalProductId != null) import_Detail_Entity.setID_FINAL_PRODUCT(finalProductId);
        if(baseProductId != null) import_Detail_Entity.setID_BASE_PRODUCT(baseProductId);
        import_Detail_Entity.setQUANTITY(quantity);
        import_Detail_Entity.setUNIT_PRICE(uPrice);
        import_Detail_Entity.setTOTAL_PRICE(pPrice);
        importGeneralCreateController.observableList =FXCollections.observableArrayList(import_Detail_Entity);
        importGeneralCreateController.setItem();
        importGeneralCreateController.closePopup(popupStage);
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            importGeneralCreateController.closePopup(popupStage);
        });

        ProductBaseService productBaseService = new ProductBaseService(entityManager);
        Product_Base_Entity productBase = new Product_Base_Entity();
        baseProductCombobox.getItems().addAll(productBaseService.getProductBaseByCombinedCondition(productBase, null, null, null, null, null, null, null));

        ProductFinalService productFinalService = new ProductFinalService(entityManager);
        Product_Final_Entity productFinal = new Product_Final_Entity();
        finalProductCombobox.getItems().addAll(productFinalService.getProductFinalByCombinedCondition(productFinal, null, null, null, null, null, null, null));

    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setImportGeneralCreateController(ImportGeneralCreateController importGeneralCreateController) {
        this.importGeneralCreateController = importGeneralCreateController;
    }


}