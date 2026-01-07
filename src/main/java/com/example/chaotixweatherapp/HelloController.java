package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private ComboBox<String> unitBox;

    @FXML
    private Button okButton;

    @FXML
    public void initialize() {
        locationBox.getItems().addAll("Wien", "Berlin", "Zürich");
        unitBox.getItems().addAll("Celsius", "Fahrenheit");

        // optional defaults to avoid null values:
        locationBox.getSelectionModel().selectFirst();
        unitBox.getSelectionModel().select("Celsius");
    }

    @FXML
    private void onOkClick() throws IOException {
        String city = locationBox.getValue();
        String unit = unitBox.getValue();

        System.out.println("OK geklickt");
        System.out.println("Standort: " + city);
        System.out.println("Einheit: " + unit);

        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("weather-view.fxml")
        );

        Parent root = loader.load();

        WeatherController controller = loader.getController();
        controller.initData(city, unit);

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
