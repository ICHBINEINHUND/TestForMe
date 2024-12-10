package com.example.dkkp.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;

public class DashboardController {
    @FXML
    private MFXComboBox<String> monthComboBox;
    @FXML
    private MFXTextField yearTextField;
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

    private List<RevenueData> getRevenueData(String month, String year) {
        return List.of(new RevenueData(1, 1000), new RevenueData(2, 1200), new RevenueData(3, 900), new RevenueData(4, 1100), new RevenueData(5, 800), new RevenueData(6, 1300));
    }

    private List<Product> getBestSellerData(String month, String year) {
        return List.of(new Product("Samsung Galaxy S24", 100), new Product("NVIDIA GeForce RTX 4090", 90), new Product("NVIDIA GeForce GTX 1660 Super", 70), new Product("NVIDIA GeForce RTX 3060", 50), new Product("AMD Threadripper 7995WX", 30), new Product("Others", 5));
    }

    @FXML
    public void onUpdateClick(ActionEvent actionEvent) {
        String selectedMonth = monthComboBox.getValue();
        String selectedYear = yearTextField.getText();
        updateStatistics(selectedMonth, selectedYear);
        updateRevenueChart(selectedMonth, selectedYear);
        updateBestSellerPieChart(selectedMonth, selectedYear);
    }

    private void updateStatistics(String month, String year) {
        totalRevenueMonth.setText("$" + getTotalRevenueForMonth(month));
        totalRevenueYear.setText("$" + getTotalRevenueForYear(year));
        bestSellerMonth.setText(getBestSellerForMonth(month));
        bestSellerYear.setText(getBestSellerForYear(year));
    }

    private double getTotalRevenueForMonth(String month) {
        return 5000;
    }

    private double getTotalRevenueForYear(String year) {
        return 50000;
    }

    private String getBestSellerForMonth(String month) {
        return "Samsung Galaxy S24";
    }

    private String getBestSellerForYear(String year) {
        return "NVIDIA GeForce RTX 4090";
    }

    private void updateRevenueChart(String month, String year) {
        List<RevenueData> revenueData = getRevenueData(month, year);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Revenue");
        for (RevenueData data : revenueData) {
            series.getData().add(new XYChart.Data<>(data.time(), data.revenue()));
        }
        revenueChart.getData().clear();
        revenueChart.getData().add(series);
    }

    private void updateBestSellerPieChart(String month, String year) {
        List<Product> bestSellers = getBestSellerData(month, year);
        bestSellerChart.getData().clear();
        for (Product product : bestSellers) {
            PieChart.Data slice = new PieChart.Data(product.name(), product.quantitySold());
            bestSellerChart.getData().add(slice);
        }
    }

    public record RevenueData(int time, double revenue) { }
    public record Product(String name, int quantitySold) { }
}