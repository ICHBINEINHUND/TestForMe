package com.example.dkkp.controller.product.productFinal;

import com.example.dkkp.controller.product.TableInterface;
import com.example.dkkp.controller.product.productBase.ProductBaseController;
import com.example.dkkp.model.*;
import com.example.dkkp.service.BrandService;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.ProductBaseService;
import com.example.dkkp.service.ProductFinalService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductFinalUpdateController  {
    private Product_Final_Entity productEntity;
    private ProductFinalController productFinalController;
//
    @FXML
    private MFXTableView<Product_Attribute_Values_Entity> optionValueTable;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> ID;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> ID_OPTION;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> VALUE;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> NAME_OPTION;


    @FXML
    private MFXTextField ID_FINAL_PRODUCT;
    @FXML
    private MFXTextField NAME_PRODUCT;
    @FXML
    private MFXTextField DES_PRODUCT;
    @FXML
    private MFXTextField PRICE;
    @FXML
    private MFXTextField QUANTITY;
    @FXML
    private MFXTextField DISCOUNT;

    @FXML
    private MFXFilterComboBox<Product_Base_Entity> baseProductField;

    @FXML
    private MFXButton updateBtn;
    @FXML
    private MFXButton backBtn;
    private Stage popupStage;

    private ObservableList<Product_Option_Values_Entity> list;

    @FXML
    public void initialize() {pushEntity();}

    public void setProductFinalController(ProductFinalController productFinalController) {
        this.productFinalController = productFinalController;

        Product_Base_Entity productBase = new Product_Base_Entity();
        ProductBaseService productBaseService = new ProductBaseService(entityManager);
        baseProductField.getItems().addAll(productBaseService.getProductBaseByCombinedCondition(productBase,null,null,null,null,null,null,null));
        Product_Base_Entity productBaseEntity = new Product_Base_Entity();
        productBaseEntity.setID_BASE_PRODUCT(productEntity.getID_BASE_PRODUCT());
        baseProductField.setText(productBaseService.getProductBaseByCombinedCondition(productBaseEntity,null,null,null,null,null,null,null).toString());;
        baseProductField.hashCode();
    }

    public void pushEntity() {
        if (productEntity != null) {
            ID_FINAL_PRODUCT.setText(productEntity.getID_SP().toString());
            NAME_PRODUCT.setText(productEntity.getNAME_PRODUCT());
            DES_PRODUCT.setText(productEntity.getDES_PRODUCT());
            PRICE.setText(String.valueOf(productEntity.getPRICE_SP()));
            QUANTITY.setText(productEntity.getQUANTITY().toString());
            DISCOUNT.setText(productEntity.getDISCOUNT().toString());
        }
    }

    public void setEntity(Product_Final_Entity product_final_entity) {
        this.productEntity = product_final_entity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}