package com.example.dkkp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductController {
  @FXML
  private StackPane main;
  @FXML
  private Button productBaseFinal;
  @FXML
  private Button productOption;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @FXML
  public void initialize() {
    loadProductBaseFinal();
  }

  @FXML
  public void loadProductBaseFinal() {
    setMainView("/com/example/dkkp/ProductBaseFinalView.fxml");
    setActiveTab(productBaseFinal);
  }

  @FXML
  public void loadProductOption() {
    setMainView("/com/example/dkkp/ProductOptionView.fxml");
    setActiveTab(productOption);
  }

  private void setMainView(String fxmlPath) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      main.getChildren().clear();
      main.getChildren().add(loader.load());
    } catch (IOException e) {
      logger.error("Loading FXML Failed!", e);
    }
  }

  private void setActiveTab(Button activeTab) {
    productBaseFinal.getStyleClass().remove("activeBtn2");
    productOption.getStyleClass().remove("activeBtn2");
    activeTab.getStyleClass().add("activeBtn2");
  }
}