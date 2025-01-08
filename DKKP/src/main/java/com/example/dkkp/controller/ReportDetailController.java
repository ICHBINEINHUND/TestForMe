package com.example.dkkp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ReportDetailController {
  @FXML
  private Button bckBtn;
  @FXML
  private StackPane main;

  @FXML
  public void initialize() {
    bck();
  }

  private void bck() {
    bckBtn.setOnAction(_ -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/Report/ReportView.fxml"));
      main.getChildren().clear();
      try {
        main.getChildren().add(loader.load());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }
}