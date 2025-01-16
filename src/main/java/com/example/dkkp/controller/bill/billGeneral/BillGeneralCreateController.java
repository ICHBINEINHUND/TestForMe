package com.example.dkkp.controller.bill.billGeneral;

import com.example.dkkp.controller.bill.BillController;
import com.example.dkkp.controller.bill.billDetail.BillDetailCreateController;
import com.example.dkkp.model.*;
import com.example.dkkp.service.BillService;
import com.example.dkkp.service.UserService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.dkkp.controller.LoginController.entityManagerFactory;

public class BillGeneralCreateController {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    @FXML
    private MFXTableView<Bill_Detail_Entity> billDetailTable;
    @FXML
    private MFXTableColumn<Bill_Detail_Entity> ID_FINAL_PRODUCT;
    @FXML
    private MFXTableColumn<Bill_Detail_Entity> QUANTITY_SP;
    @FXML
    private MFXTableColumn<Bill_Detail_Entity> UNIT_PRICE;
    @FXML
    private MFXTableColumn<Bill_Detail_Entity> TOTAL_DETAIL_PRICE;
    @FXML
    private MFXTableColumn<Bill_Detail_Entity> AVAILABLE;


    @FXML
    private MFXTextField ADD_BILL;
    @FXML
    private MFXTextField PHONE_BILL;
    @FXML
    private MFXTextField DESCRIPTION;

    @FXML
    private MFXFilterComboBox<User_Entity> ID_USER;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner hourSpinner;
    @FXML
    private Spinner minuteSpinner;
    @FXML
    private Spinner secondSpinner;


    @FXML
    private MFXButton createBtn;
    @FXML
    private MFXButton back;
    @FXML
    private MFXButton createBillDetail;
    @FXML
    private MFXButton deleteBillDetail;

    public ObservableList<Bill_Detail_Entity> observableList;
    private static final Logger logger = LoggerFactory.getLogger(BillController.class);
    BillGeneralController billGeneralController;
    BillDetailCreateController importDetailCreateController = new BillDetailCreateController();

    public List<Bill_Detail_Entity> listBillDetail = new ArrayList<>();

    @FXML
    public void createItem() throws Exception {
        LocalDateTime dateTime;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (datePicker.getValue() != null) {
            LocalDate date = datePicker.getValue();
            if (hourSpinner.getValue() != null) hour = (int) hourSpinner.getValue();
            if (minuteSpinner.getValue() != null) minute = (int) minuteSpinner.getValue();
            if (secondSpinner.getValue() != null) second = (int) secondSpinner.getValue();
            dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute, second));
        } else {
            dateTime = LocalDateTime.now();
        }


        String des = (DESCRIPTION.getText().isEmpty()) ? null : DESCRIPTION.getText();
        String idUser = (ID_USER.getValue() != null) ? ID_USER.getValue().getID_USER() : null;

        String phone = null;
        String add = null;
        System.out.println("DCM LOI HOAI");
        if (idUser != null) {
            UserService userService = new UserService(entityManager);
            phone = userService.getUsersByID(idUser).getPHONE_ACC();
            add = userService.getUsersByID(idUser).getADDRESS();
        }
        System.out.println("DCM LOI HOAI 2");
        if (!PHONE_BILL.getText().isEmpty()) phone = PHONE_BILL.getText();
        if (!ADD_BILL.getText().isEmpty()) add = ADD_BILL.getText();

        EnumType.Status_Bill billStatus = EnumType.Status_Bill.PEN;
        transaction.begin();
        try {
            Bill_Entity billEntity = new Bill_Entity( dateTime, add, phone, idUser, null, des, billStatus);
            BillService billService = new BillService(entityManager);
            billService.registerNewBill(billEntity, phone, add);
            if (!listBillDetail.isEmpty()) {
                for (Bill_Detail_Entity item : listBillDetail) {
                    System.out.println("dang trong for");
                    item.setID_BILL(billEntity.getID_BILL());
                }
            }
//            if (!listBillDetail.isEmpty()) System.out.println("trong nay nay");
            ;
            if (!listBillDetail.isEmpty()) billService.registerNewBillDetail(listBillDetail);
            billGeneralController.billController.setMainView("/com/example/dkkp/BillGeneral/BillGeneralView.fxml", billGeneralController);
            transaction.commit();
            observableList.clear();
            listBillDetail.clear();
        } catch (Exception e) {
            transaction.rollback();
        }
    }


    @FXML
    public void initialize() {
        setItem();
        setCol();
        setWidth();
        crt();
//
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)); // Giờ: 0 - 23, mặc định 12
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Phút: 0 - 59, mặc định 0
        secondSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Giây: 0 - 59, mặc định 0

        UserService userService = new UserService(entityManager);
        ID_USER.getItems().addAll(userService.getAllUser());

    }


    private void crt() {
        createBtn.setOnAction(event -> {
            try {
                createItem();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        back.setOnMouseClicked(event -> {
            observableList.clear();
            listBillDetail.clear();
            billGeneralController.billController.setMainView("/com/example/dkkp/BillGeneral/BillGeneralView.fxml", billGeneralController);
        });

        createBillDetail.setOnMouseClicked(event -> {
            try {
                importDetailCreateController.setBillGeneralCreateController(this);
                Stage popupStage = setPopView("/com/example/dkkp/BillDetail/BillDetailCreate.fxml", importDetailCreateController);
                importDetailCreateController.setPopupStage(popupStage);
            } catch (Exception e) {
                System.out.println("coi loi " + e.getMessage());
            }
        });

    }

    public void setItem() {
        observableList = FXCollections.observableArrayList(listBillDetail);
        billDetailTable.setItems(observableList);
    }

    private void setCol() {
        ID_FINAL_PRODUCT.setRowCellFactory(_ -> new MFXTableRowCell<>(Bill_Detail_Entity::getID_FINAL_PRODUCT));
        QUANTITY_SP.setRowCellFactory(_ -> new MFXTableRowCell<>(Bill_Detail_Entity::getQUANTITY_BILL));
        UNIT_PRICE.setRowCellFactory(_ -> new MFXTableRowCell<>(Bill_Detail_Entity::getUNIT_PRICE));
        TOTAL_DETAIL_PRICE.setRowCellFactory(_ -> new MFXTableRowCell<>(Bill_Detail_Entity::getTOTAL_DETAIL_PRICE));
        AVAILABLE.setRowCellFactory(_ -> new MFXTableRowCell<>(Bill_Detail_Entity::getAVAILABLE));
    }

    private void setWidth() {
        ID_FINAL_PRODUCT.prefWidthProperty().bind(billDetailTable.widthProperty().multiply(0.2));
        QUANTITY_SP.prefWidthProperty().bind(billDetailTable.widthProperty().multiply(0.2));
        UNIT_PRICE.prefWidthProperty().bind(billDetailTable.widthProperty().multiply(0.2));
        TOTAL_DETAIL_PRICE.prefWidthProperty().bind(billDetailTable.widthProperty().multiply(0.2));
        AVAILABLE.prefWidthProperty().bind(billDetailTable.widthProperty().multiply(0.2));
    }

    public void setBillGeneralController(BillGeneralController billGeneralController) {
        this.billGeneralController = billGeneralController;
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
            popupStage.setWidth(screenWidth * 0.8);
            popupStage.setHeight(screenHeight * 0.8);
            popupStage.show();
            return popupStage;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void closePopup(Stage popupStage) {
        if (popupStage != null) {
            popupStage.close();
        }
    }
}