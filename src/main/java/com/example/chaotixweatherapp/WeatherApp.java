package com.example.chaotixweatherapp;


/*
Hier haben wir die Hauptklasse der Wetterapp. Hier startet das Programm
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WeatherApp extends Application {


    // Diese Methode wird automatisch aufgerufen, wenn die App startet
    @Override
    public void start(Stage stage) throws Exception {
// Die Datei hello-view.fxml wird geladen
        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("/com/example/chaotixweatherapp/hello-view.fxml")
        );


// aus dem Layout der FXML Datei wird die Scene, also der Inhalt des Fensters
        Scene scene = new Scene(loader.load());
        stage.setTitle("Marco Miklautz, Bianca Plattner, Yasaman Ebrahimi");
        // Die Scene wird ins Fenster gesetzt und das Fenster wird sichtbar gemacht
        stage.setScene(scene);
        stage.show();
    }

    // die main Methode ist der Startknopf- Launch() startet Java FX

    public static void main(String[] args) {
        launch();
    }
}
