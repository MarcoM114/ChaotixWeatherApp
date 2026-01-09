package com.example.chaotixweatherapp;
import com.example.chaotixweatherapp.WeatherApi;
import com.google.gson.Gson;
import com.example.chaotixweatherapp.WeatherJson;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    // Array von API Klasse
    String[] weatherData = new String[2];
    WeatherApi weatherApi = new WeatherApi();

    @FXML
    private TextField locationBox;

    @FXML
    private ComboBox<String> unitBox;

    @FXML
    private Button okButton;

    @FXML
    public void initialize() {

        unitBox.getItems().addAll("Celsius", "Fahrenheit");

        // optional defaults to avoid null values:
        unitBox.getSelectionModel().select("Celsius");
    }

    @FXML
    private void onOkClick() throws IOException {

        String city = locationBox.getText();
        String unit = unitBox.getValue();

        // Abfrage an die API; return String Array; data[0]=temp   data[1]=description
        weatherData = weatherApi.getWeatherData(city, unit);

        System.out.println("OK geklickt");
        System.out.println("Standort: " + city);
        System.out.println("Einheit: " + unit);

        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("weather-view.fxml")
        );

        Parent root = loader.load();

        WeatherController controller = loader.getController();
        controller.initData(city, unit, weatherData);  //Daten werden an anderen Controller übergeben

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
