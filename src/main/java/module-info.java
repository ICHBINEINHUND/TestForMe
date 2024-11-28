module com.example.dkkp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires com.zaxxer.hikari;
    opens com.example.dkkp.model to org.hibernate.orm.core, jakarta.persistence;
    opens com.example.dkkp to javafx.fxml;
    exports com.example.dkkp;
}