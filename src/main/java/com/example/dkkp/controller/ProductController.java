package com.example.dkkp.controller;

import com.example.dkkp.model.Product_Entity;
import com.example.dkkp.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.List;

public class ProductController {
    @FXML
    private TextField txtID, txtName, txtDescription, txtCategory, txtPrice, txtViewCount, txtQuantity, txtDiscount, txtOptionValues;
    @FXML
    private TextField txtSearch;
    @FXML
    private ImageView imgProduct;
    @FXML
    private TableView<Product_Entity> tblProducts;
    @FXML
    private TableColumn<Product_Entity, String> colID, colName, colCategory;
    @FXML
    private TableColumn<Product_Entity, Double> colPrice;
    @FXML
    private TableColumn<Product_Entity, Integer> colViewCount, colQuantity;

    private final ProductService productService = new ProductService();
    private final ObservableList<Product_Entity> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind table columns
        colID.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getID_SP()));
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNAME_SP()));
        colCategory.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getID_CATEGORY()));
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPRICE_SP()));
        colViewCount.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getVIEW_COUNT()));
        colQuantity.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQUANTITY()));

        tblProducts.setItems(productList);

        loadProducts();
    }

    private void loadProducts() {
        productList.clear();
        List<Product_Entity> products = productService.getBillByCombinedCondition(new Product_Entity(), "ID_SP", "asc", 10);
        productList.addAll(products);
    }

    @FXML
    public void onSearch() {
        String keyword = txtSearch.getText();
        Product_Entity filter = new Product_Entity();
        filter.setNAME_SP(keyword);
        filter.setID_CATEGORY(keyword);

        productList.clear();
        List<Product_Entity> products = productService.getBillByCombinedCondition(filter, "ID_SP", "asc", 10);
        productList.addAll(products);
    }

    @FXML
    public void onAdd() {
        try {
            Product_Entity product = createProductFromInput();
            if (productService.createProduct(product)) {
                showAlert(Alert.AlertType.INFORMATION, "Product added successfully!");
                loadProducts();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to add product.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage());
        }
    }

    @FXML
    public void onUpdate() {
        try {
            Product_Entity product = createProductFromInput();
            try {
                productService.changeProduct(product);
                showAlert(Alert.AlertType.INFORMATION, "Product updated successfully!");
                loadProducts();
            } catch (RuntimeException e) {
                showAlert(Alert.AlertType.ERROR, "Failed to update product.");
                throw e;
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage());
        }
    }

    @FXML
    public void onDelete() {
        Product_Entity selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                productService.deleteProduct(selectedProduct.getID_SP());
                showAlert(Alert.AlertType.INFORMATION, "Product deleted successfully!");
                loadProducts();
            } catch (RuntimeException e) {
                showAlert(Alert.AlertType.ERROR, "Failed to delete product." + e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Please select a product to delete.");
        }
    }

    private Product_Entity createProductFromInput() {
        String id = txtID.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String category = txtCategory.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int viewCount = Integer.parseInt(txtViewCount.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        List<Integer> optionValues = parseOptionValues(txtOptionValues.getText());

        return new Product_Entity(id, name, description, category, price, null, viewCount, quantity, discount, optionValues);
    }

    private List<Integer> parseOptionValues(String text) {
        try {
            String[] values = text.split(",");
            return List.of(values).stream().map(Integer::parseInt).toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid option values format. Use comma-separated integers.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
