package com.example.dkkp.controller.impozt.importGeneral;

import com.example.dkkp.model.Import_Entity;
import com.example.dkkp.model.Product_Option_Entity;
import com.example.dkkp.service.ImportService;
import com.example.dkkp.service.ProductFinalService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.dkkp.controller.LoginController.entityManager;
import static com.example.dkkp.controller.LoginController.transaction;


public class ImportGeneralCreateController {

    @FXML
    private MFXTextField DESCRIPTION;
    @FXML
    private MFXFilterComboBox<Import_Entity> ID_REPLACE;
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


    ImportGeneralController importGeneralController;

    @FXML
    public void createItem() {
        LocalDateTime dateTime = LocalDateTime.now();
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (datePicker.getValue() != null) {
            LocalDate date = datePicker.getValue();
            if (hourSpinner.getValue() != null) hour = (int) hourSpinner.getValue();
            if (minuteSpinner.getValue() != null)  minute = (int) minuteSpinner.getValue();
            if (secondSpinner.getValue() != null) second = (int) secondSpinner.getValue();
            dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute, second));
        }

        // Tạo LocalDateTime


        String des = (DESCRIPTION.getText().isEmpty()) ? null : DESCRIPTION.getText();
        String idReplace = (ID_REPLACE.getValue() != null) ? ID_REPLACE.getValue().getID_IMP() : null;
        transaction.begin();
        while (true){

        try {
            Import_Entity importEntity = new Import_Entity(dateTime, des,true,idReplace,null);
            ImportService importService = new ImportService(entityManager);
            importService.registerNewImport(importEntity);
            importGeneralController.importController.setMainView("/com/example/dkkp/ImportGeneral/ImportGeneralView.fxml", importGeneralController);
            transaction.commit();
            return ;
        } catch (Exception e) {
            transaction.rollback();
        }
        }
    }


    @FXML
    public void initialize() {

        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)); // Giờ: 0 - 23, mặc định 12
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Phút: 0 - 59, mặc định 0
        secondSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Giây: 0 - 59, mặc định 0

        createBtn.setOnAction(event -> createItem());
        back.setOnMouseClicked(event -> {
            importGeneralController.importController.setMainView("/com/example/dkkp/ImportGeneral/ImportGeneralView.fxml", importGeneralController);
        });

        ImportService importService = new ImportService(entityManager);
        Import_Entity importEntity = new Import_Entity();
        ID_REPLACE.getItems().addAll(importService.getImportByCombinedCondition(importEntity, null, null, null, null, null, null));

    }


    public void setImportGeneralController(ImportGeneralController importGeneralController) {
        this.importGeneralController = importGeneralController;
    }
}