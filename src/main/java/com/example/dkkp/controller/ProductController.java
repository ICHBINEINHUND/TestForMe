package com.example.dkkp.controller;

import com.example.dkkp.controller.product.ProductBaseController;
import com.example.dkkp.controller.product.ProductDetailController;
import com.example.dkkp.controller.product.ProductOptionController;
import com.example.dkkp.controller.product.TableInterface;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductController{
  @FXML
  public StackPane main;
  @FXML
  private Button productDetail;

  @FXML
  private Button productOption;

  @FXML
  private MFXButton productDetailB;
  @FXML
  private MFXButton productOptionB;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  public ProductBaseController productBaseController = new ProductBaseController();
  public ProductOptionController productOptionController = new ProductOptionController();

  @FXML
  public void initialize() {
    loadProductBase();
    productDetail.setOnMouseClicked(event -> {loadProductBase();});
    productOption.setOnMouseClicked(event -> {loadProductOption();});
  }

  @FXML
  public void loadProductBase() {

    productBaseController.getProductController(this);
    setMainView("/com/example/dkkp/ProductBaseView.fxml",productBaseController);
    setActiveTab(productDetail);;

  }

  @FXML
  public void loadProductOption() {
    setMainView("/com/example/dkkp/ProductOptionView.fxml",productOptionController);
    setActiveTab(productOption);
  }

  public void setMainView(String fxmlPath, TableInterface controller) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      loader.setController(controller); // Gán controller vào FXMLLoader
      main.getChildren().clear();
      main.getChildren().add(loader.load());
    } catch (IOException e) {
      logger.error("Loading FXML Failed!", e.getMessage());
    }
  }

  public void clearMainView() {
      main.getChildren().clear();
  }

  private void setActiveTab(Button activeTab) {
    productDetail.getStyleClass().remove("activeProductBtn");
    productOption.getStyleClass().remove("activeProductBtn");
    activeTab.getStyleClass().add("activeProductBtn");
  }
}