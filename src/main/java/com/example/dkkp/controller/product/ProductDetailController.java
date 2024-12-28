package com.example.dkkp.controller.product;

import com.example.dkkp.model.Product_Base_Entity;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductDetailController {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction transaction = entityManager.getTransaction();

    @FXML
    private MFXTableView<Product_Base_Entity> productTable;
    @FXML
    private MFXTableColumn<Product_Base_Entity> ID_SP;
    @FXML
    private MFXTableColumn<Product_Base_Entity> NAME_SP;
    @FXML
    private MFXTableColumn<Product_Base_Entity> PRICE_SP;
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
    private MFXTableColumn<Product_Base_Entity> ID_BRAND;
    @FXML
    private MFXTableColumn<Product_Base_Entity> NAME_BRANDS;
    @FXML
    private MFXTableColumn<Product_Base_Entity> NAME_CATEGORIES;
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
    private ObservableList<Product_Base_Entity> observableList;

    @FXML
    public void initialize() {
        observableList = getProducts();
        productTable.setItems(getProducts());
        setCol();
        setWidth();
        search();
        crt();
        upd();
        del();
    }

    private void setCol() {
        ID_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_BASE_PRODUCT));
        NAME_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_PRODUCT));
        TOTAL_QUANTITY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getQUANTITY));
        DATE_RELEASE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getDATE_RELEASE));
        VIEW_COUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getVIEW_COUNT));
        DES_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getDES_PRODUCT));
        ID_BRAND.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_BRAND));
        NAME_BRANDS.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_BRAND));
        ID_CATEGORY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getID_CATEGORY));
        NAME_CATEGORIES.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Base_Entity::getNAME_CATEGORY));
    }

    private void setWidth() {
        ID_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        NAME_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.5));
        TOTAL_QUANTITY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        DATE_RELEASE.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        VIEW_COUNT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        DES_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        ID_BRAND.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        NAME_BRANDS.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        ID_CATEGORY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
        NAME_CATEGORIES.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));
    }

    private void search() {
        searchFld.setText(searchFld.getText());
    }

    private void crt() {
        crtBtn.setOnAction(_ -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductOptionView.fxml"));
            main.getChildren().clear();
            try {
                main.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void upd() {
        updBtn.setOnAction(_ -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dkkp/ProductOptionView.fxml"));
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

    private ObservableList<Product_Base_Entity> getProducts() {
//        try {
//            transaction.begin();
//            Product_Base_Entity pd = new Product_Base_Entity(null, null, null, null, "des pd", null, null, null, "Apple", null);
//            ProductBaseService productBaseService = new ProductBaseService(entityManager);
//            List<Product_Base_Entity> o = productBaseService.getProductBaseByCombinedCondition(pd, null, null, null, null, null, null, null);
//            for (Product_Base_Entity p : o) {
//                System.out.println(p.getID_BASE_PRODUCT());
//                System.out.println(p.getNAME_PRODUCT());
//                System.out.println(p.getID_CATEGORY());
//                System.out.println(p.getNAME_CATEGORY());
//                System.out.println("-----");
//            }
//            transaction.commit();
//            return FXCollections.observableArrayList(o);
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//        } finally {
//            entityManageuur.close();
//            entityManagerFactory.close();
//
//        }
            return FXCollections.observableArrayList( new Product_Base_Entity(null,"Pd 3",20, LocalDateTime.now(),"des pd",20,1,5));
    }
}