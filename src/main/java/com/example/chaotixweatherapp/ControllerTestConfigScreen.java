package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControllerTestConfigScreen {

    @FXML
    private TextField standortInput;

    @FXML
    private ComboBox<String> meinDropdown;

    @FXML
    private Button buttonOk;

    @FXML
    private void buttonClicked() {
        // 1. Wert aus dem Textfeld holen
        String stadt = standortInput.getText();

        // 2. Wert aus der ComboBox holen
        String einheit = meinDropdown.getValue();

        // 3. Ausgabe in der Konsole
        if (stadt == null || stadt.isEmpty()) {
            System.out.println("Bitte gib einen Standort ein!");
        } else {
            System.out.println("Wetter-Abfrage für: " + stadt);
            System.out.println("Gewählte Einheit: " + einheit);
        }
    }


    public void initialize() {
        meinDropdown.getItems().addAll("Celsius", "Fahrenheit");
        meinDropdown.getSelectionModel().selectFirst();
    }
}
