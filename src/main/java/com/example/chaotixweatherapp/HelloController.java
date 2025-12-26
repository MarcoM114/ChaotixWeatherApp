package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class HelloController {

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private ComboBox<String> unitBox;

    @FXML
    public void initialize() {
        locationBox.getItems().addAll(
                "Wien", "Berlin", "Zürich" // und mehrere noch dazu
        );

        unitBox.getItems().addAll(
                "Celsius", "Fahrenheit"
        );
    }

    @FXML
    private void onOkClick() {
        System.out.println("OK geklickt");
        System.out.println("Standort: " + locationBox.getValue());
        System.out.println("Einheit: " + unitBox.getValue());
    }
}
