package com.example.dkkp.controller;

import com.example.dkkp.model.Product_Entity;
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
  private Button productDetail;
  @FXML
  private Product_Entity productFilter;
  @FXML
  private Button productOption;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @FXML
  public void initialize() {
    loadProductDetail();
  }

  @FXML
  public void loadProductDetail() {
    setMainView("/com/example/dkkp/ProductDetailView.fxml");
    setActiveTab(productDetail);
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
    productDetail.getStyleClass().remove("activeProductBtn");
    productOption.getStyleClass().remove("activeProductBtn");
    activeTab.getStyleClass().add("activeProductBtn");
  }
}