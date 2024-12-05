package com.example.dkkp.util;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewUtil {
  public static void showView(Stage stage, Pane pane) {
    stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/example/dkkp/DKKP.png"))));
    Scene scene = new Scene(pane, 1280, 720);
    stage.setMinWidth(pane.minWidth(-1));
    stage.setMinHeight(pane.minHeight(-1));
    stage.setTitle("DKKP");
    stage.setScene(scene);
    stage.show();
  }
}