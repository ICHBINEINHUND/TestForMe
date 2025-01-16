package com.example.dkkp.controller.product.productBase;

import com.example.dkkp.controller.product.TableInterface;
import com.example.dkkp.model.Brand_Entity;
import com.example.dkkp.model.Category_Entity;
import com.example.dkkp.model.Product_Base_Entity;
import com.example.dkkp.service.BrandService;
import com.example.dkkp.service.CategoryService;
import com.example.dkkp.service.ProductBaseService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.dkkp.controller.LoginController.*;


public class ProductBaseCreateController implements TableInterface {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    @FXML
    private MFXFilterComboBox<Brand_Entity> brandField;
    @FXML
    private MFXFilterComboBox<Category_Entity> cateField;

    @FXML
    private MFXTextField nameField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private MFXTextField desField;
    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;

    ProductBaseController productBaseController;

    // Hàm để lấy tất cả dữ liệu và tạo Product
    @FXML
    public void createProduct() {
        String name = (nameField.getText().isEmpty()) ? null : nameField.getText();
        String des = (desField.getText().isEmpty()) ? null : desField.getText();
        Integer brandId = (brandField.getValue() != null) ? brandField.getValue().getID_BRAND() : null;
        Integer categoryId = (cateField.getValue() != null) ? cateField.getValue().getID_CATEGORY() : null;
        LocalDate releaseDate = (releaseDatePicker.getValue() != null) ? releaseDatePicker.getValue() : null;
        LocalDateTime releaseDateTime = (releaseDate != null) ? releaseDate.atTime(LocalTime.MIDNIGHT) : null;
        if (true) {
            transaction.begin();
            try {
                Product_Base_Entity product = new Product_Base_Entity(null, name, null, releaseDateTime, des, null, categoryId, brandId);
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
                productBaseService.createProductBase(product);
                transaction.commit();
                productBaseController.setMainView("/com/example/dkkp/ProductBase/ProductBaseView.fxml", productBaseController);
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }


    @FXML
    public void initialize() {
        createBtn.setOnAction(event -> createProduct());
        back.setOnMouseClicked(event -> {
            productBaseController.setMainView("/com/example/dkkp/ProductBase/ProductBaseView.fxml", productBaseController);
        });
//        getToComboBox();
        BrandService brandService = new BrandService(entityManager);
        Brand_Entity brandEntity = new Brand_Entity();
        brandField.getItems().addAll(brandService.getFilteredBrand(brandEntity, null, null, null, null));
        CategoryService categoryService = new CategoryService(entityManager);
        Category_Entity categoryEntity = new Category_Entity();
        cateField.getItems().addAll(categoryService.getFilteredCategories(categoryEntity, null, null, null, null));
    }

    @Override
    public void setWidth() {

    }

    @FXML
    public void getToComboBox() {
    }

    public void setProductBaseController(ProductBaseController productBaseController) {
        this.productBaseController = productBaseController;
    }
}