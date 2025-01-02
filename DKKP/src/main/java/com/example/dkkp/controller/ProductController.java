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
  private Button productBase;
  @FXML
  private Button productFinal;
  @FXML
  private Button productBrand;
  @FXML
  private Button productCategory;
  @FXML
  private Button productOption;
  @FXML
  private Button productAttribute;
  @FXML
  private Button productAttributeValue;
  @FXML
  private Button productOptionValue;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @FXML
  public void initialize() {
    loadProductBase();
  }

  @FXML
  public void loadProductBase() {
    setMainView("/com/example/dkkp/ProductBaseView.fxml");
    setActiveTab(productBase);
  }

  @FXML
  public void loadProductFinal() {
    setMainView("/com/example/dkkp/ProductFinalView.fxml");
    setActiveTab(productFinal);
  }

  @FXML
  public void loadProductBrand() {
    setMainView("/com/example/dkkp/ProductBrandView.fxml");
    setActiveTab(productBrand);
  }

  @FXML
  public void loadProductCategory() {
    setMainView("/com/example/dkkp/ProductCategoryView.fxml");
    setActiveTab(productCategory);
  }

  @FXML
  public void loadProductOption() {
    setMainView("/com/example/dkkp/ProductOptionView.fxml");
    setActiveTab(productOption);
  }

  @FXML
  public void loadProductOptionValue() {
    setMainView("/com/example/dkkp/ProductOptionValueView.fxml");
    setActiveTab(productOptionValue);
  }

  @FXML
  public void loadProductAttribute() {
    setMainView("/com/example/dkkp/ProductAttributeView.fxml");
    setActiveTab(productAttribute);
  }

  @FXML
  public void loadProductAttributeValue() {
    setMainView("/com/example/dkkp/ProductAttributeValueView.fxml");
    setActiveTab(productAttributeValue);
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
    productBase.getStyleClass().remove("activeProductBtn");
    productFinal.getStyleClass().remove("activeProductBtn");
    activeTab.getStyleClass().add("activeProductBtn");
  }
}