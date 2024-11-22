package com.example.dmm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label label_clickMe;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("O no no me may beo vl");
    }
    @FXML
    protected void onClickMeButtonClick() {
        label_clickMe.setText("May chan me may beo");
    }
}