package com.example.dkkp.controller.product.productOptionValue;

import com.example.dkkp.service.Validator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class ProductOptionValuesFilterController {

    @FXML
    private MFXTextField ID_OPTION;
    @FXML
    private MFXTextField NAME_OPTION;

    @FXML
    private MFXButton back;
    @FXML
    private MFXButton applyButton;
    private Stage popupStage;
    ProductOptionValuesController productOptionValuesController;
    @FXML
    public void createFilter() {
        Integer id = ID_OPTION.getText().trim().isEmpty() ? null : Integer.parseInt(ID_OPTION.getText());
        String name = NAME_OPTION.getText().trim().isEmpty() ? null : NAME_OPTION.getText();

//        productOptionValuesController.productOptionValuesEntity = new Product_Option_Values_Entity(id, name);
        productOptionValuesController.setPage(1);
        productOptionValuesController.productController.setMainView("/com/example/dkkp/ProductOption/ProductOptionView.fxml",productOptionValuesController);
        productOptionValuesController.closePopup(popupStage);
    }


    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }
    @FXML
    public void initialize() {
        setTextFormatter();
        applyButton.setOnAction(event -> createFilter());
        back.setOnMouseClicked(event -> {
            productOptionValuesController.closePopup(popupStage);
        });
    }
    private void setTextFormatter(){
        Validator validator1 = new Validator();
        ID_OPTION.delegateSetTextFormatter(validator1.formatterInteger);
    }
    public void setProductOptionValuesController(ProductOptionValuesController productOptionValuesController) {
        this.productOptionValuesController = productOptionValuesController;
    }
}