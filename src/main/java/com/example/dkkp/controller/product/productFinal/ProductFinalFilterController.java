package com.example.dkkp.controller.product.productFinal;

import com.example.dkkp.controller.product.TableInterface;
import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Base_Entity;
import com.example.dkkp.model.Product_Final_Entity;
import com.example.dkkp.service.BrandService;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.Validator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.dkkp.controller.LoginController.entityManager;


public class ProductFinalFilterController implements TableInterface {
    @FXML
    private MFXComboBox<Brand_Entity> brandComboBox;
    @FXML
    private MFXComboBox<Category_Entity> categoryCombobox;
    @FXML
    private MFXComboBox<String> dateOperatorComboBox;
    @FXML
    private MFXComboBox<String> viewCountOperatorComboBox;
    @FXML
    private MFXComboBox<String> quantityOperatorComboBox;

    @FXML
    private MFXTextField idTextField;
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField viewCountTextField;
    @FXML
    private MFXTextField quantityTextField;

    @FXML
    private DatePicker datePicker;
    @FXML
    private MFXButton back;
    @FXML
    private MFXButton applyButton;

    private Stage popupStage;

    ProductFinalController productFinalController;


    @FXML
    public void createFilter() {
        String name = nameTextField.getText().trim().isEmpty() ? null : nameTextField.getText();
        Integer id = idTextField.getText().trim().isEmpty() ? null : Integer.parseInt(idTextField.getText());
        Integer view = viewCountTextField.getText().trim().isEmpty() ? null : Integer.parseInt(viewCountTextField.getText());
        Integer quantity = quantityTextField.getText().trim().isEmpty() ? null : Integer.parseInt(quantityTextField.getText());
        Integer brandId = brandComboBox.getValue() != null ? brandComboBox.getValue().getID_BRAND() : null;
        Integer categoryId = categoryCombobox.getValue() != null ? categoryCombobox.getValue().getID_CATEGORY() : null;
        LocalDate releaseDate = datePicker.getValue() != null ? datePicker.getValue() : null;
        LocalTime time = LocalTime.MIDNIGHT;
        LocalDateTime date = releaseDate != null ? releaseDate.atTime(time) : null;

        if (dateOperatorComboBox != null) {productFinalController.typeDiscount = getValueOperator(dateOperatorComboBox.getValue());}
        if (quantityOperatorComboBox!= null) {productFinalController.typeQuantity = getValueOperator(quantityOperatorComboBox.getValue());}
        if (viewCountOperatorComboBox != null) {productFinalController.typePrice = getValueOperator(viewCountOperatorComboBox.getValue());}
//        productFinalController.productFinalEntity = new Product_Final_Entity(id,name,quantity,date,null,view,categoryId,brandId);
        productFinalController.setPage(1);
        productFinalController.productController.setMainView("/com/example/dkkp/ProductBase/ProductBaseView.fxml",productFinalController);
        productFinalController.closePopup(popupStage);
    }

    private String getValueOperator(String value) {
        if(value == null) return null;
        System.out.println(value + " day la");
        return switch (value) {
            case "Equal" -> "=";
            case "More" -> ">";
            case "Less" -> "<";
            case "Equal or More" -> "=>";
            case "Equal or Less" -> "<=";
            default -> null;
        };
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    @FXML
    public void initialize() {
        setTextFormatter();
        applyButton.setOnAction(event -> createFilter());
        back.setOnMouseClicked(event -> {
            productFinalController.closePopup(popupStage);
        });

//
        CategoryService categoryService = new CategoryService(entityManager);
        BrandService brandService = new BrandService(entityManager);
        Brand_Entity brandEntity = new Brand_Entity();
        Category_Entity categoryEntity = new Category_Entity();
        brandComboBox.getItems().addAll(brandService.getFilteredBrand(brandEntity, null, null));
        categoryCombobox.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null));
    }
    private void setTextFormatter(){
        Validator validator1 = new Validator();
        Validator validator2 = new Validator();
        Validator validator3 = new Validator();
        idTextField.delegateSetTextFormatter(validator1.formatterInteger);
        viewCountTextField.delegateSetTextFormatter(validator2.formatterInteger);
        quantityTextField.delegateSetTextFormatter(validator3.formatterInteger);
    }


    @Override
    public void setWidth() {

    }


    public void setProductFinalController(ProductFinalController productFinalController) {
        this.productFinalController = productFinalController;
    }
}