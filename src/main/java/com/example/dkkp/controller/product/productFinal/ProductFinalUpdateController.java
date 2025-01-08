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


public class ProductFinalUpdateController implements TableInterface {
    private Product_Final_Entity productEntity;
    private ProductFinalController productFinalController;

    @FXML
    private MFXTableView<Product_Attribute_Values_Entity> attributeValueTable;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> ID;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> ID_ATTRIBUTE;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> NAME_ATTRIBUTE;
    @FXML
    private MFXTableColumn<Product_Attribute_Values_Entity> VALUE;
    @FXML
    private MFXTableView<Product_Final_Entity> finalProductTable;
    @FXML
    private MFXTableColumn<Product_Final_Entity> ID_P;
    @FXML
    private MFXTableColumn<Product_Final_Entity> NAME_P;
    @FXML
    private MFXTableColumn<Product_Final_Entity> DES;

    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField name;
    @FXML
    private MFXTextField des;
    @FXML
    private MFXTextField quantity;
    @FXML
    private MFXTextField viewCount;
    @FXML
    private DatePicker date;
    @FXML
    private MFXComboBox<Brand_Entity> brandField;
    @FXML
    private MFXComboBox<Category_Entity> cateField;
    @FXML
    private MFXButton updateBtn;
    @FXML
    private MFXButton backBtn;
    private Stage popupStage;

    private ObservableList<Product_Attribute_Values_Entity> list;
    private ObservableList<Product_Final_Entity> listP;

    @FXML
    public void initialize() {
        list = getAttributeValue();
        listP = getFinalProduct();
        attributeValueTable.setItems(list);
        finalProductTable.setItems(listP);
        setCol();
        setWidth();
        CategoryService categoryService = new CategoryService(entityManager);
        BrandService brandService = new BrandService(entityManager);
        Brand_Entity brandEntity = new Brand_Entity();
//        Brand_Entity brandEntityDefault = brandService.getFilteredBrand(new Brand_Entity(productEntity.getID_BRAND(), null, null), null, null).getFirst();
        Category_Entity categoryEntity = new Category_Entity();
        pushEntity();
        backBtn.setOnMouseClicked(event -> productFinalController.closePopup(popupStage));
        updateBtn.setOnMouseClicked(event -> upDateEntity());
//        Category_Entity categoryEntityDefault = categoryService.getFilteredCategories(new Category_Entity(productEntity.getID_CATEGORY(), null), null, null).getFirst();
        brandField.getItems().addAll(brandService.getFilteredBrand(brandEntity, null, null));
        cateField.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null));
//        cateField.setText(categoryEntityDefault.toString());
//        brandField.setText(brandEntityDefault.toString());

    }

    public ObservableList<Product_Attribute_Values_Entity> getAttributeValue() {
        ProductBaseService productBaseService = new ProductBaseService(entityManager);
        Product_Attribute_Values_Entity attributeValueEntity = new Product_Attribute_Values_Entity(null, productEntity.getID_BASE_PRODUCT(), null, null);
        List<Product_Attribute_Values_Entity> p = productBaseService.getProductAttributeValuesCombinedCondition(attributeValueEntity, "ID", "asc", null, null);
        for (Product_Attribute_Values_Entity item : p) {
            System.out.println("ID " + item.getVALUE());
        }
        return FXCollections.observableArrayList(p);
    }

    public ObservableList<Product_Final_Entity> getFinalProduct() {
        ProductFinalService productFinalService = new ProductFinalService(entityManager);
        Product_Final_Entity finalProduct = new Product_Final_Entity(null, productEntity.getID_BASE_PRODUCT(),null,null,null,null, null, null);
        List<Product_Final_Entity> p = productFinalService.getProductFinalByCombinedCondition(finalProduct,null,null,null, "ID", "asc", null, null);
        for (Product_Final_Entity item : p) {
            System.out.println("ID " + item.getNAME_PRODUCT());
        }
        return FXCollections.observableArrayList(p);
    }

    public void addButton(){

    }

    @Override
    public void setWidth() {
        ID.setMinWidth(30);
        ID_ATTRIBUTE.setMinWidth(30);
        ID.prefWidthProperty().bind(attributeValueTable.widthProperty().multiply(0.13));
        ID_ATTRIBUTE.prefWidthProperty().bind(attributeValueTable.widthProperty().multiply(0.13));
        NAME_ATTRIBUTE.prefWidthProperty().bind(attributeValueTable.widthProperty().multiply(0.37));
        VALUE.prefWidthProperty().bind(attributeValueTable.widthProperty().multiply(0.37));

    }

    private void setCol() {
        ID.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Attribute_Values_Entity::getID));
        ID_ATTRIBUTE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Attribute_Values_Entity::getID_ATTRIBUTE));
        NAME_ATTRIBUTE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Attribute_Values_Entity::getNAME_ATTRIBUTE));
        VALUE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Attribute_Values_Entity::getVALUE));

        ID_P.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getID_SP));
        NAME_P.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getNAME_PRODUCT));
        DES.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getDES_PRODUCT));
    }

    public void setProductFinalController(ProductFinalController productFinalController) {
        this.productFinalController = productFinalController;
    }

    //
    public void setEntity(Product_Final_Entity product_final_entity) {
        this.productEntity = product_final_entity;
    }

    //
    public void pushEntity() {
        if (productEntity != null) {
            id.setText(productEntity.getID_BASE_PRODUCT().toString());
            name.setText(productEntity.getNAME_PRODUCT());
            des.setText(productEntity.getDES_PRODUCT());
//            quantity.setText(String.valueOf(productEntity.getTOTAL_QUANTITY()));
//            date.setValue(productEntity.getDATE_RELEASE().toLocalDate());
//            viewCount.setText(String.valueOf(productEntity.getVIEW_COUNT()));
        }
    }

    public void upDateEntity() {
        LocalTime time = LocalTime.MIDNIGHT;
        productEntity.setNAME_PRODUCT(name.getText());
//        productEntity.setDATE_RELEASE(date.getValue().atTime(time));
        productEntity.setDES_PRODUCT(des.getText());
//        productEntity.setTOTAL_QUANTITY(Integer.parseInt(quantity.getText()));
        if (brandField.getSelectionModel().getSelectedItem() != null) {
//            productEntity.setID_BRAND(brandField.getSelectionModel().getSelectedItem().getID_BRAND());
        }
        if (cateField.getSelectionModel().getSelectedItem() != null) {
//            productEntity.setID_CATEGORY(cateField.getSelectionModel().getSelectedItem().getID_CATEGORY());
        }
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
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
//                productBaseService.updateProductBase(productEntity);
                transaction.commit();
                productFinalController.setMainView("/com/example/dkkp/ProductBase/ProductBaseView.fxml", productFinalController);
                productFinalController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}