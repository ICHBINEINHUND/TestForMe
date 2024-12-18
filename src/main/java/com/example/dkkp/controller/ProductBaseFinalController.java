package com.example.dkkp.controller;

import com.example.dkkp.model.Product_Entity;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
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

public class ProductBaseFinalController {
  @FXML
  private MFXPaginatedTableView<Product_Entity> productTable;
  @FXML
  private MFXTableColumn<Product_Entity> ID_SP;
  @FXML
  private MFXTableColumn<Product_Entity> NAME_SP;
  @FXML
  private MFXTableColumn<Product_Entity> PRICE_SP;
  @FXML
  private MFXTableColumn<Product_Entity> ID_CATEGORY;
  @FXML
  private MFXTableColumn<Product_Entity> QUANTITY;
  @FXML
  private MFXTableColumn<Product_Entity> VIEW_COUNT;
  @FXML
  private MFXTableColumn<Product_Entity> DISCOUNT;
  @FXML
  private TextField searchFld;
  @FXML
  private Button crtBtn;
  @FXML
  private Button updBtn;
  @FXML
  private Button delBtn;
  @FXML
  private StackPane main;
  private ObservableList<Product_Entity> observableList;
  private FilteredList<Product_Entity> filteredList;

  @FXML
  public void initialize() {
    observableList = getProducts();
    filteredList = new FilteredList<>(observableList, _ -> true);
    productTable.setItems(filteredList);
    setCol();
    setWidth();
    search();
    crt();
    upd();
    del();
  }

  private void setCol() {
    ID_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getID_SP));
    NAME_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getNAME_SP));
    ID_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getID_CATEGORY));
    PRICE_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getPRICE_SP));
    VIEW_COUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getVIEW_COUNT));
    QUANTITY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getQUANTITY));
    DISCOUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getDISCOUNT));
  }

  private void setWidth() {
    ID_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
    NAME_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.16));
    PRICE_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
    ID_CATEGORY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
    QUANTITY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
    VIEW_COUNT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
    DISCOUNT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.14));
  }

  private void search() {
    searchFld.textProperty().addListener((_, _, newValue) -> filteredList.setPredicate(product -> {
      if (newValue == null || newValue.isEmpty()) {
        return true;
      }
      String lowerCaseFilter = newValue.toLowerCase();
      return product.getID_SP().toLowerCase().contains(lowerCaseFilter) || product.getNAME_SP().toLowerCase().contains(lowerCaseFilter) || product.getID_CATEGORY().toLowerCase().contains(lowerCaseFilter);
    }));
  }

  private void crt() {
    crtBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductCrtView.fxml"));
      main.getChildren().clear();
      try {
        main.getChildren().add(loader.load());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void upd() {
    updBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductUpdView.fxml"));
      main.getChildren().clear();
      try {
        main.getChildren().add(loader.load());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void del() {
    delBtn.setOnAction(_ -> {
      Product_Entity selected = productTable.getSelectionModel().getSelectedValue();
      if (selected != null) {
        observableList.remove(selected);
      }
    });
  }

  private ObservableList<Product_Entity> getProducts() {
    return FXCollections.observableArrayList(new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4090", null, "GPU", 2500.0, null, 10000, 5, 2.5, null), new Product_Entity("BG2022", "AMD Ryzen 9 7950X", null, "CPU", 799.0, null, 2000, 15, 10.0, null), new Product_Entity("DS501", "Samsung 980 Pro SSD", null, "Storage", 200.0, null, 5000, 25, 5.0, null));
  }
}