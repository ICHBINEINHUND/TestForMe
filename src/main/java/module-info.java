module com.example.dkkp_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    // Thêm các thư viện Hibernate và PostgreSQL
    requires org.hibernate.orm.core;    // Để sử dụng Hibernate
    requires java.persistence;
    requires java.sql;           // Để sử dụng JPA
//    requires postgresql;                 // Để sử dụng PostgreSQL JDBC Driver

    opens com.example.dkkp_app to javafx.fxml;
    exports com.example.dkkp_app;

    opens com.example.dkkp_app.model to org.hibernate.orm.core;
}
