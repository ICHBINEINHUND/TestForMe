package com.example.dkkp.controller;

import com.example.dkkp.model.Report_Entity;
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

public class ReportController {
  @FXML
  private MFXTableView<Report_Entity> productTable;
  @FXML
  private MFXTableColumn<Report_Entity> ID_REPORT;
  @FXML
  private MFXTableColumn<Report_Entity> ID_USER;
  @FXML
  private MFXTableColumn<Report_Entity> SCRIPT_BUG;
  @FXML
  private MFXTableColumn<Report_Entity> TYPE_BUG;
  @FXML
  private MFXTableColumn<Report_Entity> DATE_REPORT;
  @FXML
  private Button ftrBtn;
  @FXML
  private Button delBtn;
  @FXML
  private StackPane main;

  @FXML
  public void initialize() {
    productTable.setItems(getProducts());
    setCol();
    setWidth();
    ftr();
    del();
  }

  private void setCol() {
    ID_REPORT.setRowCellFactory(_ -> new MFXTableRowCell<>(Report_Entity::getID_REPORT));
    ID_USER.setRowCellFactory(_ -> new MFXTableRowCell<>(Report_Entity::getID_USER));
    SCRIPT_BUG.setRowCellFactory(_ -> new MFXTableRowCell<>(Report_Entity::getSCRIPT_BUG));
    TYPE_BUG.setRowCellFactory(_ -> new MFXTableRowCell<>(Report_Entity::getTYPE_BUG));
    DATE_REPORT.setRowCellFactory(_ -> new MFXTableRowCell<>(Report_Entity::getDATE_REPORT));
  }

  private void setWidth() {
    ID_REPORT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    ID_USER.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    SCRIPT_BUG.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    TYPE_BUG.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
    DATE_REPORT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.2));
  }

  private void ftr() {
    ftrBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ReportFilter.fxml"));
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

  private ObservableList<Report_Entity> getProducts() {
    return FXCollections.observableArrayList(new Report_Entity("0", "2", "4", "6", "8"));
  }
}