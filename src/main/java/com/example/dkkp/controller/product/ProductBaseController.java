package com.example.dkkp.controller.product;

import com.example.dkkp.model.Product_Base_Entity;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductBaseController implements TableInterface {
    @FXML
    private MFXTableView<Product_Base_Entity> productTable;
    @FXML
    private MFXTableColumn<Product_Base_Entity> ID_BASE_PRODUCT;
    @FXML
    private MFXTableColumn<Product_Base_Entity> NAME_PRODUCT;

    @FXML
    private MFXTableColumn<Product_Base_Entity> TOTAL_QUANTITY;
    @FXML
    private MFXTableColumn<Product_Base_Entity> DATE_RELEASE;
    @FXML
    private MFXTableColumn<Product_Base_Entity> DES_PRODUCT;
    @FXML
    private MFXTableColumn<Product_Base_Entity> VIEW_COUNT;
    @FXML
    private MFXTableColumn<Product_Base_Entity> ID_CATEGORY;
    @FXML
    private MFXTableColumn<Product_Base_Entity> NAME_CATEGORY;
    @FXML
    private MFXTableColumn<Product_Base_Entity> ID_BRAND;
    @FXML
    private MFXTableColumn<Product_Base_Entity> NAME_BRAND;
    @FXML
    private TextField searchFld;
    @FXML
    private Button crtBtn;
    @FXML
    private Button updBtn;
    @FXML
    private Button delBtn;
    @FXML
    private StackPane main;
    @FXML
    private VBox vBox;
    private ObservableList<Product_Base_Entity> observableList;
    private FilteredList<Product_Base_Entity> filteredList;

    @FXML
    public void initialize() {
//        vBox.prefWidthProperty().bind(main.widthProperty().multiply(0.7));
        observableList = getProducts();
        filteredList = new FilteredList<>(observableList, _ -> true);
        productTable.setItems(filteredList);
        setCol();
        setWidth(); // Cập nhật độ rộng khi bảng đã có chiều rộng
        crt();
        upd();
        del();
        setColumnResizableForAllColumns(true);

    }

    private void setCol() {
        ID_BASE_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_BASE_PRODUCT));
        NAME_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_PRODUCT));
        DES_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_CATEGORY));
        DATE_RELEASE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getDATE_RELEASE));
        VIEW_COUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getVIEW_COUNT));
        TOTAL_QUANTITY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getQUANTITY));
        ID_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_CATEGORY));
        NAME_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_CATEGORY));
        ID_BRAND.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_BRAND));
        NAME_BRAND.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_BRAND));
    }


    public void setWidth() {
        ID_BASE_PRODUCT.setMinWidth(100);
        ID_BASE_PRODUCT.setMaxWidth(200);

        NAME_PRODUCT.setMinWidth(150);
        NAME_PRODUCT.setMaxWidth(250);

        DES_PRODUCT.setMinWidth(200);
        DES_PRODUCT.setMaxWidth(300);

        DATE_RELEASE.setMinWidth(150);
        DATE_RELEASE.setMaxWidth(250);

        VIEW_COUNT.setMinWidth(100);
        VIEW_COUNT.setMaxWidth(150);

        TOTAL_QUANTITY.setMinWidth(100);
        TOTAL_QUANTITY.setMaxWidth(150);

        ID_CATEGORY.setMinWidth(150);
        ID_CATEGORY.setMaxWidth(200);

        NAME_CATEGORY.setMinWidth(150);
        NAME_CATEGORY.setMaxWidth(250);

        ID_BRAND.setMinWidth(150);
        ID_BRAND.setMaxWidth(200);

        NAME_BRAND.setMinWidth(200);
        NAME_BRAND.setMaxWidth(300);
    }


    private void crt() {
//        crtBtn.setOnAction(_ -> {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductCrtView.fxml"));
//            main.getChildren().clear();
//            try {
//                main.getChildren().add(loader.load());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    private void upd() {
        updBtn.setOnAction(_ -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductUpdView.fxml"));
            main.getChildren().clear();
            try {
                main.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void del() {
        delBtn.setOnAction(_ -> {
            Product_Base_Entity selected = productTable.getSelectionModel().getSelectedValue();
            if (selected != null) {
                observableList.remove(selected);
            }
        });
    }

    private void setColumnResizableForAllColumns(boolean resizable) {
        ID_BASE_PRODUCT.setColumnResizable(resizable);
        NAME_PRODUCT.setColumnResizable(resizable);
        DES_PRODUCT.setColumnResizable(resizable);
        DATE_RELEASE.setColumnResizable(resizable);
        VIEW_COUNT.setColumnResizable(resizable);
        TOTAL_QUANTITY.setColumnResizable(resizable);
        ID_CATEGORY.setColumnResizable(resizable);
        NAME_CATEGORY.setColumnResizable(resizable);
        ID_BRAND.setColumnResizable(resizable);
        NAME_BRAND.setColumnResizable(resizable);
        // Lặp qua tất cả các cột bạn có và thiết lập cho mỗi cột
//        addResizeListener(ID_BASE_PRODUCT);
//        addResizeListener(NAME_PRODUCT);
//        addResizeListener(DES_PRODUCT);
//        addResizeListener(DATE_RELEASE);
//        addResizeListener(VIEW_COUNT);
//        addResizeListener(TOTAL_QUANTITY);
//        addResizeListener(ID_CATEGORY);
//        addResizeListener(NAME_CATEGORY);
//        addResizeListener(ID_BRAND);
//        addResizeListener(NAME_BRAND);

    }

//    private void addResizeListener(MFXTableColumn<?> column) {
//        column.widthProperty().addListener((observable, oldValue, newValue) -> {
//            // In ra thông báo mỗi khi có thay đổi kích thước cột
//            System.out.println("Column resized: " + column.getText() + " - New width: " + newValue);
//            System.out.println(productTable.getWidth());
//        });
//    }


    private ObservableList<Product_Base_Entity> getProducts() {
        return FXCollections.observableArrayList( new Product_Base_Entity(1, "NVIDIA GeForce RTX 4090", 23, LocalDateTime.now(), "des", 2, 1, 5, "dcm brand", "dcm cate"));

    }
}