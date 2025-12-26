package com.example.chaotixweatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WeatherApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("hello-view.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Marco Miklautz, Bianca Plattner, Yasaman Ebrahimi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
