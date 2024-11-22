module com.example.dmm {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.dmm to javafx.fxml;
    exports com.example.dmm;
}