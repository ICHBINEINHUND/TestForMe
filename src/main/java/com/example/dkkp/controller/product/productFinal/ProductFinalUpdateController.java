package com.example.dkkp.controller.product.productFinal;

import com.example.dkkp.controller.product.TableInterface;
import com.example.dkkp.controller.product.productBase.ProductBaseController;
import com.example.dkkp.model.*;
import com.example.dkkp.service.*;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductFinalUpdateController {
    private Product_Final_Entity productEntity;
    private ProductFinalController productFinalController;
    //
    @FXML
    private MFXTableView<Product_Option_Values_Entity> optionValueTable;
    @FXML
    private MFXTableColumn<Product_Option_Values_Entity> ID_OPTION_VALUE;
    @FXML
    private MFXTableColumn<Product_Option_Values_Entity> ID_OPTION;
    @FXML
    private MFXTableColumn<Product_Option_Values_Entity> VALUE;
    @FXML
    private MFXTableColumn<Product_Option_Values_Entity> NAME_OPTION;


    @FXML
    private MFXTextField ID_FINAL_PRODUCT;
    @FXML
    private MFXTextField NAME_PRODUCT;
    @FXML
    private MFXTextField DES_PRODUCT;
    @FXML
    private MFXTextField PRICE;
    @FXML
    private MFXTextField QUANTITY;
    @FXML
    private MFXTextField DISCOUNT;

    @FXML
    private ImageView imageView;
    @FXML
    private MFXButton image;

    @FXML
    private MFXFilterComboBox<Product_Base_Entity> baseProductField;

    @FXML
    private MFXButton updateBtn;
    @FXML
    private MFXButton backBtn;
    private Stage popupStage;

    private ObservableList<Product_Option_Values_Entity> list;

    String imagePath = null;

    @FXML
    public void initialize() {
        pushEntity();
        list = getOptionValue();
        optionValueTable.setItems(list);
        setCol();
        image.setOnAction(event -> handleOpenFile());
        updateBtn.setOnAction(event -> updateFinalProduct());
        backBtn.setOnMouseClicked(event -> productFinalController.closePopup(popupStage));
        setTextFormatter();
        Product_Base_Entity productBase = new Product_Base_Entity();
        ProductBaseService productBaseService = new ProductBaseService(entityManager);
        baseProductField.getItems().addAll(productBaseService.getProductBaseByCombinedCondition(productBase, null, null, null, null, null, null, null));
        Product_Base_Entity productBaseEntity = new Product_Base_Entity();
        if (productEntity.getID_BASE_PRODUCT() != null) {
            productBaseEntity.setID_BASE_PRODUCT(productEntity.getID_BASE_PRODUCT());
            baseProductField.setText(productBaseService.getProductBaseByCombinedCondition(productBaseEntity, null, null, null, null, null, null, null).toString());
        }
        ;
    }

    public void setImageView() {
            Path currentDir = Path.of(System.getProperty("user.dir"));
            Path imageDir = currentDir.resolve("src/main/IMAGE");
        try {
            Path imagePath;
             imagePath = imageDir.resolve(productEntity.getIMAGE_SP());
            File imageFile = imagePath.toFile();
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
        } catch (Exception e) {
                File defaultImageFile = imageDir.resolve("default.png").toFile();
                imageView.setImage(new Image(defaultImageFile.toURI().toString()));
        }
    }

    private void updateFinalProduct() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to update this item?");
        alert.setContentText("This action cannot be undone.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            try {
                transaction.begin();

                if (baseProductField.getSelectionModel().getSelectedItem() != null) {
                    productEntity.setID_BASE_PRODUCT(baseProductField.getSelectionModel().getSelectedItem().getID_BASE_PRODUCT());
                }
                productEntity.setNAME_PRODUCT(NAME_PRODUCT.getText());
                productEntity.setDES_PRODUCT(DES_PRODUCT.getText());
                productEntity.setPRICE_SP(Double.parseDouble(PRICE.getText()));
                productEntity.setQUANTITY(Integer.parseInt(QUANTITY.getText()));
                productEntity.setDISCOUNT(Double.parseDouble(DISCOUNT.getText()));

                if (imagePath == null) {
                    productEntity.setIMAGE_SP(productEntity.getIMAGE_SP());
                } else {
                    Path currentDir = Path.of(System.getProperty("user.dir"));
                    Path destinationDir = currentDir.resolve("src/main/IMAGE");
                    File sourceFile = new File(imagePath);

                    if (!Files.exists(destinationDir)) {
                        try {
                            Files.createDirectories(destinationDir);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    String fileExtension = getFileExtension(sourceFile.getName());
                    String newFileName = System.currentTimeMillis() + fileExtension;
                    Path destinationPath = destinationDir.resolve(newFileName);

                    try {
                        Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        productEntity.setIMAGE_SP(newFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ProductFinalService productFinalService = new ProductFinalService(entityManager);
                productFinalService.updateProductFinal(productEntity);
                transaction.commit();
                productFinalController.setMainView("/com/example/dkkp/ProductFinal/ProductFinalView.fxml", productFinalController);
                productFinalController.closePopup(popupStage);
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0) {
            return fileName.substring(lastIndex); // Trả về phần mở rộng như ".png"
        }
        return "";
    }

    @FXML
    private void handleOpenFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chose a picture file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) image.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            long fileSizeInBytes = selectedFile.length();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024);
            if (fileSizeInMB > 50) {
                System.out.println("File's size is too huge! Please choose a file below 50MB.");
                return;
            }
            Image selectedImage = new Image(selectedFile.toURI().toString());
            imageView.setImage(selectedImage);

            imagePath = selectedFile.getAbsolutePath();
        }
    }

    public void setProductFinalController(ProductFinalController productFinalController) {
        this.productFinalController = productFinalController;

    }

    private void setCol() {
        ID_OPTION_VALUE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Option_Values_Entity::getID));
        ID_OPTION.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Option_Values_Entity::getID_OPTION));
        VALUE.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Option_Values_Entity::getVALUE));
        NAME_OPTION.setRowCellFactory(_ -> new MFXTableRowCell<>(Product_Option_Values_Entity::getName_Option));
    }

    private void setTextFormatter(){
        Validator validator2 = new Validator();
        Validator validator3 = new Validator();
        Validator validator4 = new Validator();
        QUANTITY.delegateSetTextFormatter(validator2.formatterInteger);
        PRICE.delegateSetTextFormatter(validator3.formatterDouble);
        DISCOUNT.delegateSetTextFormatter(validator4.formatterDouble);
    }

    public void pushEntity() {
        if (productEntity != null) {
            ID_FINAL_PRODUCT.setText(productEntity.getID_SP().toString());
            NAME_PRODUCT.setText(productEntity.getNAME_PRODUCT());
            DES_PRODUCT.setText(productEntity.getDES_PRODUCT());
            PRICE.setText(String.valueOf(productEntity.getPRICE_SP()));
            QUANTITY.setText(productEntity.getQUANTITY().toString());
            DISCOUNT.setText(productEntity.getDISCOUNT().toString());
        }
    }

    public ObservableList<Product_Option_Values_Entity> getOptionValue() {
        ProductFinalService productFinalService = new ProductFinalService(entityManager);
        Product_Option_Values_Entity optionValueEntity = new Product_Option_Values_Entity(null, null, null, productEntity.getID_SP());
        List<Product_Option_Values_Entity> p = productFinalService.getProductOptionValuesCombinedCondition(optionValueEntity, null, null, null, null);
        return FXCollections.observableArrayList(p);
    }

    public void setEntity(Product_Final_Entity product_final_entity) {
        this.productEntity = product_final_entity;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

}