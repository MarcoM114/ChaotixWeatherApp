package com.example.chaotixweatherapp;
/*
Dieser Controller steuert den Bildschirm, der das Wetter anzeigt.
Also den hauptbildschirm nach der Startseite
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.io.IOException;
import java.util.Objects;

public class weather_view_controller {

    //

    String unitSymbol;

    @FXML
    private Label headlineLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Button commentButton;


    @FXML
    private ImageView iconView;



// Diese Methode wird aufgerufen, sobald der Controller geladen ist., also Stadt, Einheit und die Wetterdaten der API
    public void initData(String city, String unit, String[] weatherData) {


// das Einheit-Symbol wird hier automatisch zugeordnet mit einer einfachen if-else Anweisung
        if (unit.equals("Celsius")){
            unitSymbol ="°C";
        }else {
            unitSymbol = "°F";
        }

        // Hier werden die Wetterinformationen für die jeweiligen Labels, die zuvor in Java FX erstellt wurden
        // zugewiesen
        String condition = weatherData[1];
        headlineLabel.setText("In " + city + " hat es gerade:");
        tempLabel.setText(weatherData[0] + " " + unitSymbol);
        conditionLabel.setText(condition + ":");


        // Kommentare witzig, werden hier erstellt, mit ganz einfachen if-else Anweisungen
        String commentText;
        String c = condition.toLowerCase();
        if (c.contains("schnee")) {
            commentText = "❄ Oaschkalt...";
        } else if (c.contains("bewölkt")) {
            commentText = "Da könn ma nur eins sagen:";
        } else if (c.contains("regen")) {
            commentText = "🌧 Bussi, Baba! I bleib daham!";
        } else if (c.contains("klar")) {
            commentText = "☀ Ur Geiles Wetta!";
        } else if (c.contains("gewitter")) {
            commentText = "⚡ na bist du gscheit!";
        } else if (c.contains("nebel")) {
            commentText = "🌫 oida, I siag nix aussi!";
        } else {
            commentText = "";
        }

        // der KommentarButton wird hier hinzugefügt
        // wenn der Button geklickt wird wird das POPUP FXML geladen
        // dann bekommt der Controller des Pop UPs den text und das Meme und das POP UP wird angezeigt

        commentButton.setOnAction(e -> {
            if (!commentText.isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/example/chaotixweatherapp/pop-up.fxml")
                    );
                    VBox root = loader.load();

                    // Controller richtig referenzieren
                    pop_up_controller popupController = loader.getController();
                    popupController.setCommentText(commentText);

                    // Meme passend zur Wetterbedingung
                    switch (c) {
                        case "schnee" -> popupController.setMemeImage("/com/example/chaotixweatherapp/memes/schnee_meme.png");
                        case "regen" -> popupController.setMemeImage("/com/example/chaotixweatherapp/memes/regen_meme.png");
                        case "klar" -> popupController.setMemeImage("/com/example/chaotixweatherapp/memes/klar_meme.png");
                        case "gewitter" -> popupController.setMemeImage("/com/example/chaotixweatherapp/memes/gewitter_meme.png");
                        case "nebel" -> popupController.setMemeImage("/com/example/chaotixweatherapp/memes/nebel_meme.png");
                        case "bewölkt" -> popupController.setMemeImage("/com/example/chaotixweatherapp/memes/bewölkt_meme.png");
                    }

                    Stage popup = new Stage();
                    popup.initOwner(commentButton.getScene().getWindow());
                    popup.setTitle("Wetter-Kommentar");
                    popup.setScene(new javafx.scene.Scene(root));
                    popup.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });




// Hier wird das Wettericon ausgewählt
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

        // Macht den Wettertext klein und sauber (to lowercase and trim)
        String d = condition.trim().toLowerCase();

        // Wählt anhand des Texts das passende Icon
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

    // Zurück zur Startseite mit dem Back-Button

    @FXML
    private void onBackClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("hello-view.fxml")
        );
        Parent root = loader.load();

        Stage stage = (Stage) headlineLabel.getScene().getWindow();
        stage.setScene(new Scene(root , stage.getWidth(), stage.getHeight()));
        stage.show();
    }
}
