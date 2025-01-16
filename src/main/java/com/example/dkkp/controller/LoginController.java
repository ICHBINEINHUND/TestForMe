package com.example.dkkp.controller;

import com.example.dkkp.util.ViewUtil;
import com.example.dkkp.view.HomeView;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.util.Objects;

import io.github.palexdev.materialfx.controls.MFXTextField;

public class LoginController {

  static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
   public EntityManager entityManager = entityManagerFactory.createEntityManager();
    public EntityTransaction transaction = entityManager.getTransaction();
  @FXML
  private MFXTextField username;
  @FXML
  private MFXPasswordField password;

  @FXML
  private void handleLogin(ActionEvent event) {
    if (username != null && password != null) {
      HomeView homeView = new HomeView();
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      homeView.showHomeView(currentStage);
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
      alertStage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/example/dkkp/DKKP.png"))));
      alert.setTitle("");
      alert.setHeaderText(null);
      alert.setContentText("Invalid Credentials");
      alert.showAndWait();
    }
  }
}