module com.example.dkkp {
  requires com.zaxxer.hikari;
  requires io.hypersistence.utils.hibernate.type;
  requires jakarta.persistence;
  requires java.sql;
  requires MaterialFX;
  requires org.hibernate.orm.core;
  requires org.slf4j;
  opens com.example.dkkp to javafx.fxml;
  opens com.example.dkkp.controller;
  opens com.example.dkkp.model to org.hibernate.orm.core, jakarta.persistence;
  exports com.example.dkkp;
  exports com.example.dkkp.controller;
}