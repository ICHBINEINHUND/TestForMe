package com.example.dkkp.controller.product.productBrand;

import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.Product_Base_Entity;
import com.example.dkkp.service.BrandService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ProductBrandCreateController {

    @FXML
    private MFXTextField NAME_BRAND;
    @FXML
    private MFXTextField DETAIL;

    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;
   

    ProductBrandController productBrandController;
    @FXML
    public void createProduct() {
        String name = (NAME_BRAND.getText().isEmpty()) ? null : NAME_BRAND.getText();
        String des = (DETAIL.getText().isEmpty()) ? null : DETAIL.getText();
            transaction.begin();
            try {

                Brand_Entity brandEntity = new Brand_Entity(name, des);
                BrandService brandService = new BrandService(entityManager);
                brandService.createNewBrand(brandEntity);
                transaction.commit();
                productBrandController.productController.setMainView("/com/example/dkkp/Brand/ProductBrandView.fxml", productBrandController);
            } catch (Exception e) {
                transaction.rollback();
            }
//        }
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            productBrandController.productController.setMainView("/com/example/dkkp/Brand/ProductBrandView.fxml", productBrandController);
        });
    }


    public void setProductBrandController(ProductBrandController productBrandController) {
        this.productBrandController = productBrandController;
    }
}