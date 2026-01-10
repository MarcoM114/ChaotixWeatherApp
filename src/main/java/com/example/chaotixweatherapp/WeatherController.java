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

public class WeatherController {

    String unitSymbol;

    @FXML
    private Label headlineLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label descripLabel;

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

        headlineLabel.setText("In " + city + " hat es gerade:");
        tempLabel.setText(weatherData[0] + " " + unitSymbol);
        conditionLabel.setText(weatherData[1] + ":");

        // optional icon switch
        // put files like snowflake.png / rain.png into resources same folder as FXML
        /*
        String iconFile = switch (data.iconKey()) {
            case "snow" -> "snowflake.png";
            case "rain" -> "rain.png";
            case "sun"  -> "sun.png";
            default     -> "cloud.png";
            };

        try {
            Image img = new Image(getClass().getResourceAsStream(iconFile));
            iconView.setImage(img);
        } catch (Exception ignored) {
            // if icon missing, just keep whatever is set in FXML
        }

         */
    }

    @FXML
    private void onBackClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("hello-view.fxml")
        );
        Parent root = loader.load();

        Stage stage = (Stage) headlineLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
