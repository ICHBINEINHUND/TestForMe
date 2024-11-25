module com.example.dkkp_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.dkkp_app to javafx.fxml;
    exports com.example.dkkp_app;
}