package com.example.dkkp.controller.product.productAttributeValue;

import com.example.dkkp.controller.product.TableInterface;
import com.example.dkkp.model.Product_Base_Entity;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductAttributeValueController {
//  @FXML
//  private MFXTableView<Product_Base_Entity> productTable;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> ID_BASE_PRODUCT;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> NAME_PRODUCT;
//
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> TOTAL_QUANTITY;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> DATE_RELEASE;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> DES_PRODUCT;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> VIEW_COUNT;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> ID_CATEGORY;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> NAME_CATEGORY;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> ID_BRAND;
//  @FXML
//  private MFXTableColumn<Product_Base_Entity> NAME_BRAND;
//  @FXML
//  private TextField searchFld;
//  @FXML
//  private Button crtBtn;
//  @FXML
//  private Button updBtn;
//  @FXML
//  private Button delBtn;
//  @FXML
//  private StackPane main;
//  private ObservableList<Product_Base_Entity> observableList;
//  private FilteredList<Product_Base_Entity> filteredList;
//
//  @FXML
//  public void initialize() {
//    observableList = getProducts();
//    filteredList = new FilteredList<>(observableList, _ -> true);
//    productTable.setItems(filteredList);
//    setCol();
//    setWidth();
//    search();
//    crt();
//    upd();
//    del();
//  }
//
//  private void setCol() {
//    ID_BASE_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_BASE_PRODUCT));
//    NAME_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_PRODUCT));
//    DES_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_CATEGORY));
//    DATE_RELEASE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getDATE_RELEASE));
//    VIEW_COUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getVIEW_COUNT));
//    TOTAL_QUANTITY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getTOTAL_QUANTITY));
//    ID_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_CATEGORY));
//    NAME_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_CATEGORY));
//    ID_BRAND.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_BRAND));
//    NAME_BRAND.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_BRAND));
//
//  }
//
//  public void setWidth() {
//    ID_BASE_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    NAME_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.16));
//    DES_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    DATE_RELEASE.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    VIEW_COUNT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    TOTAL_QUANTITY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    ID_CATEGORY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    NAME_CATEGORY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    ID_BRAND.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//    NAME_BRAND.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
//  }
//
//  private void search() {
//    searchFld.textProperty().addListener((_, _, newValue) -> filteredList.setPredicate(product -> {
////      if (newValue == null || newValue.isEmpty()) {
////        return true;
////      }
////      String lowerCaseFilter = newValue.toLowerCase();
////      return product.getID_SP().toLowerCase().contains(lowerCaseFilter) || product.getNAME_SP().toLowerCase().contains(lowerCaseFilter) || product.getID_CATEGORY().toLowerCase().contains(lowerCaseFilter);
//      return false;
//    }
//    ));
//  }
//
//  private void crt() {
//    crtBtn.setOnAction(_ -> {
//      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductBase/ProductBaseCreate.fxml"));
//      main.getChildren().clear();
//      try {
//        main.getChildren().add(loader.load());
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    });
//  }
//
//  private void upd() {
//    updBtn.setOnAction(_ -> {
//      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductBase/ProductBaseUpdate.fxml"));
//      main.getChildren().clear();
//      try {
//        main.getChildren().add(loader.load());
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    });
//  }
//
//  private void del() {
//    delBtn.setOnAction(_ -> {
//      Product_Base_Entity selected = productTable.getSelectionModel().getSelectedValue();
//      if (selected != null) {
//        observableList.remove(selected);
//      }
//    });
//  }
//
//  private ObservableList<Product_Base_Entity> getProducts() {
//    return FXCollections.observableArrayList(new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"),new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"),new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"),new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"),new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"),new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"),new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"));
//  }
}