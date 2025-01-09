package com.example.dkkp.controller.product.productFinal;

import com.example.dkkp.controller.product.ProductController;
import com.example.dkkp.controller.product.TableInterface;
import com.example.dkkp.controller.product.productBase.ProductBaseController;
import com.example.dkkp.controller.product.productBase.ProductBaseCreateController;
import com.example.dkkp.controller.product.productBase.ProductBaseFilterController;
import com.example.dkkp.model.Product_Base_Entity;
import com.example.dkkp.model.Product_Final_Entity;
import com.example.dkkp.service.ProductBaseService;
import com.example.dkkp.service.ProductFinalService;
import com.example.dkkp.service.Validator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;

public class ProductFinalController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @FXML
    private MFXTableView<Product_Final_Entity> productTable;
    @FXML
    private MFXTableColumn ID_FINAL_PRODUCT;
    @FXML
    private MFXTableColumn ID_BASE_PRODUCT;
    @FXML
    private MFXTableColumn NAME_PRODUCT;
    @FXML
    private MFXTableColumn QUANTITY;
    @FXML
    private MFXTableColumn DES_PRODUCT;
    @FXML
    private MFXTableColumn PRICE_SP;
    @FXML
    private MFXTableColumn DISCOUNT;

    Product_Final_Entity productFinalEntity = new Product_Final_Entity();
    ProductFinalService productFinalService = new ProductFinalService(entityManager);

    @FXML
    private Label totalRowLabel;
    @FXML
    private Label numberSetOff;
    @FXML
    private Button searchFld;
    @FXML
    private Button crtBtn;
    @FXML
    private Button updBtn;
    @FXML
    private Button delBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private StackPane main;
    @FXML
    private HBox paginationHBox;
    @FXML
    private MFXTextField setOffField;

    public ProductController productController;
    public ProductFinalCreateController productFinalCreateController = new ProductFinalCreateController() ;
    public ProductFinalFilterController productFinalFilterController = new ProductFinalFilterController() ;
    public ProductFinalUpdateController productFinalUpdateController = new ProductFinalUpdateController() ;

    String sortField = null;
    String sortOrder = null;

    String typePrice = null;
    String typeDiscount = null;
    String typeQuantity = null;

    Integer setOff = 2;
    Integer offSet = 0;

    @FXML
    private MFXButton prevBtn, prevPageBtn, nextPageBtn, nextBtn;
    @FXML
    private Label pageLabel1, pageLabel2, pageLabel3;
    public int currentPage = 1;
    private int totalPages = 5;

    private ObservableList<Product_Final_Entity> observableList;
    @FXML
    public void initialize() {
        observableList = getProducts();
        productTable.setItems(observableList);
        setCol();
        setWidth();
        updateTotalPage();
        crt();
        setSort();

        Validator validator1 = new Validator();
        setOffField.delegateSetTextFormatter(validator1.formatterInteger);
    }

    private void crt() {
        main.setOnMouseClicked(event -> {
            productTable.getSelectionModel().clearSelection();
            main.requestFocus();
        });
        setOffField.setOnKeyPressed(event -> handleKeyPress(event));
        crtBtn.setOnAction(_ -> {
            productFinalCreateController.setProductFinalController(this);
            setMainView("/com/example/dkkp/ProductFinal/ProductFinalCreate.fxml", productFinalCreateController);
        });

        updatePagination();
        refreshBtn.setOnMouseClicked(event -> {
            ProductFinalController productFinalControllerNew = new ProductFinalController();
            productFinalControllerNew.productController = this.productController;
            productController.productFinalController = productFinalControllerNew;
            productController.setMainView("//com/example/dkkp/ProductFinal/ProductFinalView.fxml", productFinalControllerNew);
        });

        searchFld.setOnMouseClicked(event -> {
            ProductFinalFilterController productFinalFilterController = new ProductFinalFilterController();
            productFinalFilterController.setProductFinalController(this);
            Stage popupStage = setPopView("/com/example/dkkp/ProductFinal/ProductBaseFilter.fxml", productFinalFilterController);
            productFinalFilterController.setPopupStage(popupStage);  // Truyền Stage cho controller của popup
        });
        updBtn.setOnMouseClicked(event -> upd());
        delBtn.setOnMouseClicked(event -> del());

        pageLabel1.setOnMouseClicked(event -> setPage(currentPage));
        pageLabel2.setOnMouseClicked(event -> setPage(currentPage + 1));
        pageLabel3.setOnMouseClicked(event -> setPage(currentPage + 2));
        prevBtn.setOnAction(event -> setPage(1));
        nextBtn.setOnAction(event -> setPage(totalPages));
        prevPageBtn.setOnAction(event -> setPage(currentPage - 1));
        nextPageBtn.setOnAction(event -> setPage(currentPage + 1));
    }

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    private void setWidth() {
        ID_FINAL_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        ID_BASE_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        NAME_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.3));
        QUANTITY.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        DES_PRODUCT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        PRICE_SP.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        DISCOUNT.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
    }

    private void setCol() {
        ID_FINAL_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getID_SP));
        ID_BASE_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getID_BASE_PRODUCT));
        NAME_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getNAME_PRODUCT));
        QUANTITY.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getQUANTITY));
        DES_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getDES_PRODUCT));
        PRICE_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getPRICE_SP));
        DISCOUNT.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Final_Entity::getDISCOUNT));
    }

    private void setSort() {

        ID_FINAL_PRODUCT.setOnMouseClicked(event -> handleSort("ID_SP"));
        ID_BASE_PRODUCT.setOnMouseClicked(event -> handleSort("ID_BASE_PRODUCT"));
        NAME_PRODUCT.setOnMouseClicked(event -> handleSort("NAME_PRODUCT"));
        QUANTITY.setOnMouseClicked(event -> handleSort("QUANTITY"));
        DES_PRODUCT.setOnMouseClicked(event -> handleSort("DES_PRODUCT"));
        PRICE_SP.setOnMouseClicked(event -> handleSort("PRICE_SP"));
        DISCOUNT.setOnMouseClicked(event -> handleSort("DISCOUNT"));
    }

    private void handleSort(String columnName) {
        if (sortField == null || !sortField.equals(columnName)) {
            sortField = columnName;
            sortOrder = "asc";
        } else {
            sortOrder = sortOrder.equals("asc") ? "desc" : "asc";
        }
        refreshProductTable();
    }
    private void handleKeyPress(KeyEvent event) {
        // Kiểm tra nếu phím nhấn là Enter
        if (event.getCode() == KeyCode.ENTER) {
            setOff = Integer.parseInt(setOffField.getText().trim());
            updatePagination();
            updateTotalPage();
            refreshProductTable();
            if(currentPage > totalPages) currentPage = totalPages;
            productController.setMainView("/com/example/dkkp/ProductFinal/ProductFinalView.fxml",this);
        }
    }

    public void refreshProductTable() {
        observableList = getProducts();
        productTable.setItems(observableList);
        updatePagination();
        updateTotalPage();
    }
    public void setPage(int page) {
        if (page < 1 || page > totalPages || totalPages == 1) {
            return;
        }
        offSet = ((page - 1) * setOff);
        currentPage = page;
        updatePagination();
        refreshProductTable();
    }
    private void updatePagination() {
        if (totalPages < 3) {
            if (totalPages == 1) {
                System.out.println("total Page" + totalPages);
                pageLabel2.setManaged(false);
            }
            pageLabel3.setManaged(false);
            if (totalPages == 0) {
                paginationHBox.setVisible(false);
                paginationHBox.setManaged(false);
            }
        }
        if (currentPage == 1) {
            pageLabel1.setText("1");
            pageLabel2.setText("2");
            pageLabel3.setText("3");

            pageLabel1.setDisable(true);
            pageLabel2.setDisable(false);
            pageLabel3.setDisable(false);
            pageLabel2.setOnMouseClicked(event -> setPage(2));
            pageLabel3.setOnMouseClicked(event -> setPage(3));
        } else if (currentPage == totalPages && totalPages == 2) {
            pageLabel1.setText("1");
            pageLabel2.setText("2");

            pageLabel2.setDisable(true);
            pageLabel1.setDisable(false);

            pageLabel1.setOnMouseClicked(event -> setPage(1));

        } else if (currentPage == totalPages) {
            pageLabel1.setText(String.valueOf(totalPages - 2));
            pageLabel2.setText(String.valueOf(totalPages - 1));
            pageLabel3.setText(String.valueOf(totalPages));

            pageLabel1.setDisable(false);
            pageLabel2.setDisable(false);
            pageLabel3.setDisable(true);

            pageLabel1.setOnMouseClicked(event -> setPage(totalPages - 2));
            pageLabel2.setOnMouseClicked(event -> setPage(totalPages - 1));
            pageLabel3.setOnMouseClicked(event -> setPage(totalPages));
        } else {
            pageLabel1.setText(String.valueOf(currentPage - 1));
            pageLabel2.setText(String.valueOf(currentPage));
            pageLabel3.setText(String.valueOf(currentPage + 1));

            pageLabel1.setDisable(false);
            pageLabel2.setDisable(true);
            pageLabel3.setDisable(false);

            pageLabel1.setOnMouseClicked(event -> setPage(currentPage - 1));
            pageLabel2.setOnMouseClicked(event -> setPage(currentPage));
            pageLabel3.setOnMouseClicked(event -> setPage(currentPage + 1));
//            }

            if (pageLabel1.isDisable()) {
                pageLabel1.setStyle("-fx-text-fill: gray;");
            } else {
                pageLabel1.setStyle("-fx-text-fill: black;");
            }
            if (pageLabel2.isDisable()) {
                pageLabel2.setStyle("-fx-text-fill: gray;");
            } else {
                pageLabel2.setStyle("-fx-text-fill: black;");
            }
            if (pageLabel3.isDisable()) {
                pageLabel3.setStyle("-fx-text-fill: gray;");
            } else {
                pageLabel3.setStyle("-fx-text-fill: black;");
            }

        }

        prevBtn.setDisable(currentPage == 1);
        prevPageBtn.setDisable(currentPage == 1);
        nextBtn.setDisable(currentPage == totalPages || totalPages == 3);
        nextPageBtn.setDisable(currentPage == totalPages || totalPages == 3);
    }
    private void del() {
        List<Product_Final_Entity> selectedItems = productTable.getSelectionModel().getSelectedValues();
        if (!selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this item?");
            alert.setContentText("This action cannot be undone.");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                try {
                    transaction.begin();
                    ProductFinalService productFinalService = new ProductFinalService(entityManager);
                    for (Product_Final_Entity item : selectedItems) {
                        productFinalService.deleteProductFinal(item.getID_BASE_PRODUCT());
                    }
                    transaction.commit();
                } catch (Exception e) {
                    transaction.rollback();
                    throw e;
                }
                refreshProductTable();
            }
        }
    }

    private void upd() {
        List<Product_Final_Entity> selectedItems = productTable.getSelectionModel().getSelectedValues();
        if (selectedItems.size() == 1) {
            productFinalUpdateController.setEntity(selectedItems.getFirst());
            productFinalUpdateController.setProductFinalController(this);
            Stage popupStageUpdate = setPopView("/com/example/dkkp/ProductFinal/ProductFinalUpdate.fxml", productFinalUpdateController);
            productFinalUpdateController.setPopupStage(popupStageUpdate);
        }
        ;
    }

    private ObservableList<Product_Final_Entity> getProducts() {
        List<Product_Final_Entity> p = productFinalService.getProductFinalByCombinedCondition(productFinalEntity,typePrice, typeDiscount,  typeQuantity, sortField, sortOrder, setOff, offSet);
        for(Product_Final_Entity item : p) {
            System.out.println("Day la "+ item.getNAME_PRODUCT());
        }
        return FXCollections.observableArrayList(p);
    }
    private void updateTotalPage() {
        Integer number = productFinalService.getCountProductFinalByCombinedCondition(productFinalEntity, typePrice, typeQuantity, typeDiscount);
        totalPages = (int) Math.ceil((double) number / setOff);
        totalRowLabel.setText("Total row : " +number);
        numberSetOff.setText("Number row per page: " +setOff);
    }

    public void setMainView(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(controller);
            main.getChildren().clear();
            main.getChildren().add(loader.load());
        } catch (IOException e) {
            logger.error("Loading FXML Failed!", e.getMessage());
        }
    }

    public Stage setPopView(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(controller);
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Base product filter");
            Scene scene = new Scene(loader.load());
            popupStage.setScene(scene);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            popupStage.setWidth(screenWidth *0.8);
            popupStage.setHeight(screenHeight *0.8);
            popupStage.show();
            return popupStage;
        } catch (IOException e) {
            logger.error("Loading FXML Failed!", e.getMessage());
            return null;
        }
    }
    public void closePopup(Stage popupStage) {
        if (popupStage != null) {
            popupStage.close();
        }
    }
}