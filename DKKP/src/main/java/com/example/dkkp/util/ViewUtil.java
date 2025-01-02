package com.example.dkkp.util;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewUtil {
  private static double width = 1280;
  private static double height = 720;

  public static void showView(Stage stage, Pane pane) {
    stage.getIcons().add(new Image(Objects.requireNonNull(ViewUtil.class.getResourceAsStream("/com/example/dkkp/DKKP.png"))));
    Scene scene = new Scene(pane, 1280, 720);
    double stageWidth = (width > 0) ? width : 1280;
    double stageHeight = (height > 0) ? height : 720;
    stage.setWidth(stageWidth);
    stage.setHeight(stageHeight);
    stage.setMinWidth(pane.minWidth(-1));
    stage.setMinHeight(pane.minHeight(-1));
    stage.setTitle("DKKP");
    stage.setScene(scene);
    stage.show();
    stage.widthProperty().addListener((_, _, newValue) -> width = newValue.doubleValue());
    stage.heightProperty().addListener((_, _, newValue) -> height = newValue.doubleValue());
  }
}