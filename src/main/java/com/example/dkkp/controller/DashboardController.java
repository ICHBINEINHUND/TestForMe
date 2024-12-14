package com.example.dkkp.controller;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardController {
  @FXML
  private MFXDatePicker datePicker;
  @FXML
  private Label totalRevenueMonth;
  @FXML
  private Label totalRevenueYear;
  @FXML
  private Label bestSellerMonth;
  @FXML
  private Label bestSellerYear;
  @FXML
  private LineChart<Number, Number> revenueChart;
  @FXML
  private PieChart bestSellerChart;
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MMMM yyyy");

  @FXML
  public void initialize() {
    configDatePicker();
    onUpdate();
  }

  private void configDatePicker() {
    datePicker.setConverterSupplier(() -> new StringConverter<>() {
      @Override
      public String toString(LocalDate date) {
        return date != null ? date.format(DTF) : "";
      }

      @Override
      public LocalDate fromString(String string) {
        return string != null && !string.isEmpty() ? LocalDate.parse(string, DTF) : null;
      }
    });
    datePicker.setValue(LocalDate.now());
    datePicker.setYearsRange(new NumberRange<>(2024, 2050));
  }

  @FXML
  public void onUpdate() {
    updateStatistics();
    updateLineChart();
    updatePieChart();
  }

  private void updateStatistics() {
    totalRevenueMonth.setText(String.format("$%.2f", getTotalRevenueForMonth()));
    totalRevenueYear.setText(String.format("$%.2f", getTotalRevenueForYear()));
    bestSellerMonth.setText(getBestSellerForMonth());
    bestSellerYear.setText(getBestSellerForYear());
  }

  private void updateLineChart() {
    List<RevenueData> revenueData = getRevenueData();
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName("Revenue");
    revenueData.forEach(data -> series.getData().add(new XYChart.Data<>(data.time(), data.revenue())));
    revenueChart.getData().clear();
    revenueChart.getData().add(series);
  }

  private void updatePieChart() {
    List<Product> bestSellers = getBestSellerData();
    bestSellerChart.getData().clear();
    bestSellers.forEach(product -> bestSellerChart.getData().add(new PieChart.Data(product.name(), product.quantity())));
  }

  private List<RevenueData> getRevenueData() {
    return List.of(new RevenueData(1, 1000), new RevenueData(2, 1200), new RevenueData(3, 900), new RevenueData(4, 1100), new RevenueData(5, 800), new RevenueData(6, 1800));
  }

  private List<Product> getBestSellerData() {
    return List.of(new Product("Samsung Galaxy S24", 100), new Product("NVIDIA GeForce RTX 4090", 90), new Product("NVIDIA GeForce GTX 1660 Super", 70), new Product("NVIDIA GeForce RTX 3060", 50), new Product("AMD Threadripper 7995WX", 30), new Product("Others", 5));
  }

  private double getTotalRevenueForMonth() {
    return 5000;
  }

  private double getTotalRevenueForYear() {
    return 80000;
  }

  private String getBestSellerForMonth() {
    return "Samsung Galaxy S24";
  }

  private String getBestSellerForYear() {
    return "NVIDIA GeForce RTX 4090";
  }

  public record RevenueData(int time, double revenue) {}
  public record Product(String name, int quantity) {}
}