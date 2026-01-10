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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloController {

    // Array für API WeatherApi Objekt
    String[] weatherData = new String[2];
    WeatherApi weatherApi = new WeatherApi();

    @FXML
    private TextField locationBox;

    @FXML
    private ComboBox<String> unitBox;

    @FXML
    private Button okButton;

    @FXML private Label errorLabel;

    @FXML
    public void initialize() {

        unitBox.getItems().addAll("Celsius", "Fahrenheit");

        // optional defaults to avoid null values:
        unitBox.getSelectionModel().select("Celsius");
        if (errorLabel != null) errorLabel.setText("");
        okButton.setDefaultButton(true);

        locationBox.setOnAction(e -> {
            try { onOkClick(); } catch (IOException ex) { ex.printStackTrace(); }
        });

    }

    @FXML
    private void onOkClick() throws IOException {
        if (errorLabel != null) errorLabel.setText("");

        String city = locationBox.getText()== null ? "" : locationBox.getText().trim();
        String unit = unitBox.getValue();
        if (city.isEmpty()) {
            if (errorLabel != null) errorLabel.setText("Bitte gib einen Standort ein.");
            return;
        }

        // Abfrage an die API; return String Array;
        // weatherData[0]=temp   weatherData[1]=description

        weatherData = weatherApi.getWeatherData(city, unit);
        if (weatherData == null || weatherData.length < 2 || "FEHLER".equals(weatherData[0])) {
            String msg = (weatherData != null && weatherData.length >= 2) ? weatherData[1] : "Unbekannter Fehler.";
            if (errorLabel != null) errorLabel.setText(msg);
            return;
        }


        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("weather-view.fxml")
        );
        Parent root = loader.load();

        WeatherController controller = loader.getController();
        controller.initData(city, unit, weatherData);  //Daten werden an anderen Controller übergeben

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.setScene(new Scene(root, 520, 520));
        stage.show();


}

    }
