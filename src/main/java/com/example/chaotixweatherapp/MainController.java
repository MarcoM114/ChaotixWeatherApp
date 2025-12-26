package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private API api;

    @FXML
    private Button mybutton;

    @FXML
    private TextField mytextfield;

    @FXML
    public void initialize() {
        api = new API();
        System.out.println("MainController instantiated");
    }

    @FXML
    private void backBtnClicked() throws IOException {
        System.out.println("button clicked!");
        System.out.println("Eingabe: " + mytextfield.getText());

        // Scene wechseln zur hello-view.fxml
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("hello-view.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) mybutton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
