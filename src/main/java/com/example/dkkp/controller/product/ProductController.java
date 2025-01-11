package com.example.dkkp.controller.product;

import com.example.dkkp.controller.product.productAttribute.ProductAttributeController;
import com.example.dkkp.controller.product.productBase.ProductBaseController;
import com.example.dkkp.controller.product.productBrand.ProductBrandController;
import com.example.dkkp.controller.product.productCategory.ProductCategoryController;
import com.example.dkkp.controller.product.productFinal.ProductFinalController;
import com.example.dkkp.controller.product.productOption.ProductOptionController;
import com.example.dkkp.controller.product.productOptionValue.ProductOptionValueController;
import com.example.dkkp.controller.product.productAttributeValue.ProductAttributeValueController;
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
  private Button productBase;
  @FXML
  private Button productFinal;
   @FXML
  private Button productBrand;
   @FXML
  private Button productCategory;
   @FXML
  private Button productAttribute;
   @FXML
  private Button productAttributeValue;
   @FXML
  private Button productOption;
   @FXML
  private Button productOptionValue;

  @FXML
  private MFXButton productDetailB;
  @FXML
  private MFXButton productOptionB;
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  public ProductBaseController productBaseController = new ProductBaseController();
  public ProductFinalController productFinalController = new ProductFinalController();
  public ProductOptionController productOptionController = new ProductOptionController();
  public ProductOptionValueController productOptionValueController = new ProductOptionValueController();
  public ProductAttributeController productAttributeController = new ProductAttributeController();
  public ProductAttributeValueController productAttributeValueController = new ProductAttributeValueController();
  public ProductBrandController productBrandController = new ProductBrandController();
  public ProductCategoryController productCategoryController = new ProductCategoryController();

  
  @FXML
  public void initialize() {
    productBaseController.setProductController(this);
    getButton();
    setActiveTab(productBase);
    setMainView("/com/example/dkkp/ProductBase/ProductBaseView.fxml",productBaseController);
  }
  private void getButton(){
    productBase.setOnAction(event -> {
      productBaseController.setProductController(this);
      setActiveTab(productBase);
      setMainView("/com/example/dkkp/ProductBase/ProductBaseView.fxml",productBaseController);
    });
    productFinal.setOnMouseClicked(event -> {
      productFinalController.setProductController(this);
      setActiveTab(productFinal);
      setMainView("/com/example/dkkp/ProductFinal/ProductFinalView.fxml",productFinalController);
    });
    productBrand.setOnMouseClicked(event -> {
      setActiveTab(productBrand);
      setMainView("/com/example/dkkp/Brand/ProductBrandView.fxml/", productBrandController);
    });
    productCategory.setOnMouseClicked(event -> {
      setActiveTab(productCategory);
      setMainView("/com/example/dkkp/Category/ProductCategoryView.fxml/", productCategoryController);
    });
    productAttribute.setOnMouseClicked(event -> {
      setActiveTab(productAttribute);
      setMainView("/com/example/dkkp/ProductAttribute/ProductAttributeView.fxml/", productAttributeController);
    });
    productAttributeValue.setOnMouseClicked(event -> {
      setActiveTab(productAttributeValue);
      setMainView("/com/example/dkkp/ProductAttributeValue/ProductAttributeValueView.fxml/", productAttributeValueController);
    });
    productOption.setOnMouseClicked(event -> {
      setActiveTab(productOption);
      setMainView("/com/example/dkkp/ProductOption/ProductOptionView.fxml/", productOptionController);
    });
    productOptionValue.setOnMouseClicked(event -> {
      setActiveTab(productOptionValue);
      setMainView("/com/example/dkkp/ProductOptionValue/ProductOptionValueView.fxml/", productOptionValueController);
    });
  }


  public void setMainView(String fxmlPath, Object controller) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      loader.setController(controller); // Gán controller vào FXMLLoader
      main.getChildren().clear();
      main.getChildren().add(loader.load());
    } catch (IOException e) {
      System.out.printf("Loi " +e.getMessage());
//      logger.error("Loading FXML Failed!" + e.getMessage()) ;
    }
  }


  private void setActiveTab(Button activeTab) {
    productBase.getStyleClass().remove("activeProductBtn");
    productFinal.getStyleClass().remove("activeProductBtn");
    productAttribute.getStyleClass().remove("activeProductBtn");
    productAttributeValue.getStyleClass().remove("activeProductBtn");
    productOption.getStyleClass().remove("activeProductBtn");
    productOptionValue.getStyleClass().remove("activeProductBtn");
    productBrand.getStyleClass().remove("activeProductBtn");
    productCategory.getStyleClass().remove("activeProductBtn");
    activeTab.getStyleClass().add("activeProductBtn");
  }
}