package com.example.dkkp.controller;

import com.example.dkkp.util.ViewUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import io.github.palexdev.materialfx.controls.MFXTextField;
import com.example.dkkp.view.HomeView;

import java.util.Objects;

public class LoginController {
  @FXML
  private MFXTextField username;
  @FXML
  private MFXTextField password;

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