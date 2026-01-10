package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;



import java.io.IOException;
import java.util.Objects;

public class WeatherController {

    String unitSymbol;

    @FXML
    private Label headlineLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private ImageView iconView;


    /**
     * Call this right after loading the FXML (from HelloController).
     */
    public void initData(String city, String unit, String[] weatherData) {



        if (unit.equals("Celsius")){
            unitSymbol ="°C";
        }else {
            unitSymbol = "°F";
        }
        String condition = weatherData[1];
        headlineLabel.setText("In " + city + " hat es gerade:");
        tempLabel.setText(weatherData[0] + " " + unitSymbol);
        conditionLabel.setText(condition + ":");

        String iconFile = getIconFile(condition);
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/" + iconFile)));
            iconView.setImage(img);
        } catch (Exception e) {
            System.err.println("Icon not found: icons/" + iconFile);
        }

    }
    private String getIconFile(String condition) {
        if (condition == null) return "cloudy.png";

        // normalize (lowercase, trim)
        String d = condition.trim().toLowerCase();

        return switch (d) {
            case "gewitter" -> "storm.png";
            case "regen"    -> "rain.png";
            case "schnee"   -> "snow.png";
            case "nebel"    -> "nebel.png";
            case "klar"     -> "clear.png";
            case "bewölkt"  -> "cloudy.png";
            default         -> "cloudy.png";
        };
    }

    @FXML
    private void onBackClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("hello-view.fxml")
        );
        Parent root = loader.load();

        Stage stage = (Stage) headlineLabel.getScene().getWindow();
        stage.setScene(new Scene(root , 520, 520));
        stage.show();
    }
}
