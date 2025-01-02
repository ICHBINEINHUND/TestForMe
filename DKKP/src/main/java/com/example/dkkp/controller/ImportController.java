package com.example.dkkp.controller;

import com.example.dkkp.model.Import_Entity;
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

public class ImportController {
  @FXML
  private MFXTableView<Import_Entity> productTable;
  @FXML
  private MFXTableColumn<Import_Entity> ID_IMP;
  @FXML
  private MFXTableColumn<Import_Entity> ID_REPLACE;
  @FXML
  private MFXTableColumn<Import_Entity> IS_AVAILABLE;
  @FXML
  private MFXTableColumn<Import_Entity> TOTAL_PRICE;
  @FXML
  private MFXTableColumn<Import_Entity> DATE_IMP;
  @FXML
  private MFXTableColumn<Import_Entity> DESCRIPTION;
  @FXML
  private Button ftrBtn;
  @FXML
  private Button crtBtn;
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
    crt();
    del();
    prt();
  }

  private void setCol() {
    ID_IMP.setRowCellFactory(_ -> new MFXTableRowCell<>(Import_Entity::getID_IMP));
    ID_REPLACE.setRowCellFactory(_ -> new MFXTableRowCell<>(Import_Entity::getID_REPLACE));
    IS_AVAILABLE.setRowCellFactory(_ -> new MFXTableRowCell<>(Import_Entity::getIS_AVAILABLE));
    DATE_IMP.setRowCellFactory(_ -> new MFXTableRowCell<>(Import_Entity::getDATE_IMP));
    TOTAL_PRICE.setRowCellFactory(_ -> new MFXTableRowCell<>(Import_Entity::getTOTAL_PRICE));
    DESCRIPTION.setRowCellFactory(_ -> new MFXTableRowCell<>(Import_Entity::getDESCRIPTION));
  }

  private void setWidth() {
    ID_IMP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    ID_REPLACE.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    IS_AVAILABLE.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    DATE_IMP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    TOTAL_PRICE.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    DESCRIPTION.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
  }

  private void ftr() {
    ftrBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ImportFilter.fxml"));
      main.getChildren().clear();
      try {
        main.getChildren().add(loader.load());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void crt() {
    crtBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ImportCreate.fxml"));
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

  private ObservableList<Import_Entity> getProducts() {
    return FXCollections.observableArrayList(new Import_Entity("0", "2", "4", "6", "8", "10"));
  }
}