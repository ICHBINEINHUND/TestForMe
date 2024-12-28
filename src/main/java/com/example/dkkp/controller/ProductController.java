package com.example.dkkp.controller;

import com.example.dkkp.controller.product.ProductBaseController;
import com.example.dkkp.controller.product.ProductDetailController;
import com.example.dkkp.controller.product.ProductOptionController;
import com.example.dkkp.controller.product.TableInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductController  {
  @FXML
  private StackPane main;
  @FXML
  private Button productDetail;

  @FXML
  private Button productOption;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  private ProductBaseController productBaseController;
  private ProductOptionController productOptionController;

  @FXML
  public void initialize() {
    loadProductBase();
  }

  @FXML
  public void loadProductBase() {

    setMainView("/com/example/dkkp/ProductBaseView.fxml");
    setActiveTab(productDetail);;

  }

  @FXML
  public void loadProductOption() {
    if(productOptionController == null) {
      productOptionController = new ProductOptionController();
    }
    setMainView("/com/example/dkkp/ProductOptionView.fxml",productOptionController);
    setActiveTab(productOption);
  }

  private void setMainView(String fxmlPath, TableInterface controller) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      loader.setController(controller); // Gán controller vào FXMLLoader

      main.getChildren().clear();
      main.getChildren().add(loader.load());
    } catch (IOException e) {
      logger.error("Loading FXML Failed!", e.getMessage());
    }
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