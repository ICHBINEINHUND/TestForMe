module com.example.dkkp {
  requires jakarta.persistence;
  requires org.hibernate.orm.core;
  requires com.zaxxer.hikari;
  requires java.sql;
  requires org.slf4j;
  requires MaterialFX;
  requires io.hypersistence.utils.hibernate.type;
  opens com.example.dkkp to javafx.fxml;
  opens com.example.dkkp.controller;
  opens com.example.dkkp.model to org.hibernate.orm.core, jakarta.persistence;
  exports com.example.dkkp;
  exports com.example.dkkp.controller;
}