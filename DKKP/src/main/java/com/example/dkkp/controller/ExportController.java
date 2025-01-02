package com.example.dkkp.controller;

import com.example.dkkp.model.Export_Entity;
import com.example.dkkp.util.ViewUtil;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ExportController {
  @FXML
  private MFXTableView<Export_Entity> productTable;
  @FXML
  private MFXTableColumn<Export_Entity> ID_BILL;
  @FXML
  private MFXTableColumn<Export_Entity> ID_USER;
  @FXML
  private MFXTableColumn<Export_Entity> PHONE_BILL;
  @FXML
  private MFXTableColumn<Export_Entity> ADD_BILL;
  @FXML
  private MFXTableColumn<Export_Entity> BILL_STATUS;
  @FXML
  private MFXTableColumn<Export_Entity> TOTAL_PRICE;
  @FXML
  private MFXTableColumn<Export_Entity> DATE_EXP;
  @FXML
  private MFXTableColumn<Export_Entity> DESCRIPTION;
  @FXML
  private Button ftrBtn;
  @FXML
  private Button delBtn;
  @FXML
  private Button prtBtn;
  @FXML
  private StackPane main;

  @FXML
  public void initialize() {
    productTable.setItems(getProducts());
    setCol();
    setWidth();
    ftr();
    del();
    prt();
  }

  private void setCol() {
    ID_BILL.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getID_BILL));
    ID_USER.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getID_USER));
    PHONE_BILL.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getPHONE_BILL));
    ADD_BILL.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getADD_BILL));
    BILL_STATUS.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getBILL_STATUS));
    TOTAL_PRICE.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getTOTAL_PRICE));
    DATE_EXP.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getDATE_EXP));
    DESCRIPTION.setRowCellFactory(_ -> new MFXTableRowCell<>(Export_Entity::getDESCRIPTION));
  }

  private void setWidth() {
    ID_BILL.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    ID_USER.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    PHONE_BILL.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    ADD_BILL.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    BILL_STATUS.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    TOTAL_PRICE.prefWidthProperty().bind(productTable.widthProperty().multiply(0.15));
    DATE_EXP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    DESCRIPTION.prefWidthProperty().bind(productTable.widthProperty().multiply(0.15));
  }

  private void ftr() {
    ftrBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ExportFilter.fxml"));
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
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
      alertStage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/example/dkkp/DKKP.png"))));
      alert.setTitle("");
      alert.setHeaderText(null);
      alert.setContentText("Are You Sure You Want To Delete This Item?");
      alert.showAndWait();
    });
  }

  private void prt() {
    prtBtn.setOnAction(_ -> {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
      alertStage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/example/dkkp/DKKP.png"))));
      alert.setTitle("");
      alert.setHeaderText(null);
      alert.setContentText("Saved Successfully At \"Documents\"!");
      alert.showAndWait();
    });
  }

  private ObservableList<Export_Entity> getProducts() {
    return FXCollections.observableArrayList(new Export_Entity("0", "2", "4", "6", "8", "10", "12", "14"));
  }
}