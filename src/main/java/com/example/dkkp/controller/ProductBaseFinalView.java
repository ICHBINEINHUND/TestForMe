package com.example.dkkp.controller;
import com.example.dkkp.model.Product_Entity;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
public class ProductBaseFinalView {
  @FXML
  private MFXPaginatedTableView<Product_Entity> paginatedTable;
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
  public void initialize() {
    ObservableList<Product_Entity> productList = getHardcodedProducts();
    paginatedTable.setItems(productList);
    ID_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getID_SP));
    NAME_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getNAME_SP));
    ID_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getID_CATEGORY));
    PRICE_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getPRICE_SP));
    VIEW_COUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getVIEW_COUNT));
    QUANTITY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getQUANTITY));
    DISCOUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Entity::getDISCOUNT));
  }
  private ObservableList<Product_Entity> getHardcodedProducts() {
    return FXCollections.observableArrayList(new Product_Entity("APTX-4869", "NVIDIA GeForce RTX 4090", null, "GPU", 2500.0, null, 10000, 5, 2.5, null), new Product_Entity("BG2022", "AMD Ryzen 9 7950X", null, "CPU", 799.0, null, 2000, 15, 10.0, null), new Product_Entity("DS501", "Samsung 980 Pro SSD", null, "Storage", 200.0, null, 5000, 25, 5.0, null));
  }
}