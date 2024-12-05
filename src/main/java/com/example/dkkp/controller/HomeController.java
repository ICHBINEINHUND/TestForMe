package com.example.dkkp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeController {
  @FXML
  private StackPane main;
  @FXML
  private Button dashboardTab;
  @FXML
  private Button productTab;
  @FXML
  private Button importTab;
  @FXML
  private Button exportTab;
  @FXML
  private Button reportTab;
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @FXML
  public void initialize() {
    loadDashboardView();
    setActiveTab(dashboardTab);
  }

  @FXML
  public void loadDashboardView() {
    setMainView("/com/example/dkkp/DashboardView.fxml");
    setActiveTab(dashboardTab);
  }

  @FXML
  public void loadProductView() {
    setMainView("/com/example/dkkp/ProductView.fxml");
    setActiveTab(productTab);
  }

  @FXML
  public void loadImportView() {
    setMainView("/com/example/dkkp/ImportView.fxml");
    setActiveTab(importTab);
  }

  @FXML
  public void loadExportView() {
    setMainView("/com/example/dkkp/ExportView.fxml");
    setActiveTab(exportTab);
  }

  @FXML
  public void loadReportView() {
    setMainView("/com/example/dkkp/ReportView.fxml");
    setActiveTab(reportTab);
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
    dashboardTab.getStyleClass().remove("activeBtn");
    productTab.getStyleClass().remove("activeBtn");
    importTab.getStyleClass().remove("activeBtn");
    exportTab.getStyleClass().remove("activeBtn");
    reportTab.getStyleClass().remove("activeBtn");
    activeTab.getStyleClass().add("activeBtn");
  }
}