package com.example.chaotixweatherapp;

/*
Hierbei handelt es sich um den Code für die Startseite der Wetterapp. Die Aufgaben des Codes sind
für den Benutzer die Stadt einzugeben und die Temperatureinheit auszuwählen. Um die Stadt einzugeben ist
eine direkte Verbindung zu der API notwendig. Zudem drückt der Benutzer nach den Voreinstellungen auf einen
"OK" Button. Danach holt das Programm die Wetterdaten von der API und die zweite Seite öffnet.

 */

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

// das ist der Controller des ersten Bildschirmes
public class hello_view_controller {

    // Array für API WeatherApi Objekt
    String[] weatherData = new String[2];
    WeatherApi weatherApi = new WeatherApi();


    // nun werden die Variablen definiert, die mit den FXML Layouts für die erste Seite verbunden sind

    // Stadteingabefeld
    @FXML
    private TextField locationBox;

    //Auswahl für Celsius und Fahrenheit

    @FXML
    private ComboBox<String> unitBox;

    // OK Button

    @FXML
    private Button okButton;

    // Textfeld, falls eine Fehlermeldung kommt

    @FXML private Label errorLabel;


    // Diese Methode läuft automatisch, sobald der Bildschirm angezeigt wird.

    @FXML
    public void initialize() {

        unitBox.getItems().addAll("Celsius", "Fahrenheit");

        // Standardmäßig ist Celsius ausgewählt.
        unitBox.getSelectionModel().select("Celsius");

        //wenn es keinen Fehler gibt, soll das textfeld leer sein, damit z.B keine
        // alten Fehlermeldungen geladen werden
        if (errorLabel != null) errorLabel.setText("");




        // mit dieser Methode reagiert der "OK" BUtton auf "ENTER"
        okButton.setDefaultButton(true);

        locationBox.setOnAction(e -> {
            try { onOkClick(); } catch (IOException ex) { ex.printStackTrace(); }
        });
    }
    @FXML
    private void onOkClick() throws IOException {
        // alte Fehlermeldungen werden gelöscht:
        if (errorLabel != null) errorLabel.setText("");
        // Stadt und Einheit werden von dem Programm ausgelesen
        String city = locationBox.getText()== null ? "" : locationBox.getText().trim();
        String unit = unitBox.getValue();
        // prüfen ob die Stadt leer ist
        if (city.isEmpty()) {
            if (errorLabel != null) errorLabel.setText("Bitte gib einen Standort ein.");
            return;
        }

        // Abfrage an die API; return String Array;
        // weatherData[0]=temp   weatherData[1]=description

        // Wetterdaten aus dem Internet holen und überprüft, ob
        // Daten angekommen sind, zu wenig Daten angekommen sind -> wenn dem so ist= Fehler

        weatherData = weatherApi.getWeatherData(city, unit);
        if (weatherData == null || weatherData.length < 2 || "FEHLER".equals(weatherData[0])) {
            String msg = (weatherData != null && weatherData.length >= 2) ? weatherData[1] : "Unbekannter Fehler.";
            if (errorLabel != null) errorLabel.setText(msg);
            return;
        }


        //Wechsel zu dem neuen Bildschirm "weather- view.fxml" und Zugriff auf den neuen Controller

        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("weather-view.fxml")
        );
        Parent root = loader.load();

        weather_view_controller controller = loader.getController();
        controller.initData(city, unit, weatherData);  //Daten werden an anderen Controller übergeben


        // Aktuelles Fenster wird ersetzt. mit der Größe 520x520

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();


}

    }
