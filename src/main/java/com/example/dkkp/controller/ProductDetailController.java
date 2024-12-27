package com.example.dkkp.controller;

import com.example.dkkp.model.Product_Entity;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ProductDetailController {
  @FXML
  private MFXTableView<Product_Entity> productTable;
  @FXML
  private MFXTableColumn<Product_Entity> ID_SP;
  @FXML
  private MFXTableColumn<Product_Entity> NAME_SP;
  @FXML
  private MFXTableColumn<Product_Entity> PRICE_SP;
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

  @FXML
  public void initialize() {
    observableList = getProducts();
    productTable.setItems(getProducts());
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
    PRICE_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getPRICE_SP));
  }

  private void setWidth() {
    ID_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
    NAME_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.5));
    PRICE_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
  }

  private void search() {
    searchFld.setText(searchFld.getText());
  }

  private void crt() {
    crtBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductOptionView.fxml"));
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
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductOptionView.fxml"));
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
    return FXCollections.observableArrayList(new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4090", 2500.5), new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4080", 2500.5), new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4070", 2500.5), new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4060", 2500.5), new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4050", 2500.5));
  }
}