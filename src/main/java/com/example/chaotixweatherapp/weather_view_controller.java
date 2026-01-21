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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class weather_view_controller {



    protected String unitSymbol;
//   adding sound : https://youtu.be/J07HiaaYwis?si=edsPJFgDwctaxf0w
    private boolean soundEnabled = true;
    private Clip weatherClip;
    private String lastCondition = "";

    @FXML
    private Label headlineLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Button commentButton;
    @FXML
    private Button soundButton;
    @FXML
    private ImageView iconView;

    // Diese Methode wird aufgerufen, sobald der Controller geladen ist., also Stadt, Einheit und die Wetterdaten der API
    public void initData(String city, String unit, String[] weatherData) {

        // das Einheit-Symbol wird hier automatisch zugeordnet mit einer einfachen if-else Anweisung
        if (unit.equals("Celsius")) {
            unitSymbol = "°C";
        } else {
            unitSymbol = "°F";
        }

        // Hier werden die Wetterinformationen für die jeweiligen Labels, die zuvor in Java FX erstellt wurden
        // zugewiesen
        String condition = weatherData[1];
        headlineLabel.setText("In " + city + " hat es gerade:");
        tempLabel.setText(weatherData[0] + " " + unitSymbol);
        conditionLabel.setText(condition + ":");
        lastCondition = condition;
        updateSoundButtonUI ( );
       updateWeatherSound ( condition );
        // Kommentare witzig, werden hier erstellt, mit ganz einfachen if-else Anweisungen
        String commentText;
        String c = condition.toLowerCase();
        if (c.contains("schnee")) {
            commentText = "❄ Oaschkalt...";
        } else if (c.contains("bewölkt")) {
            commentText = "Ur schirch draußen...";
        } else if (c.contains("regen")) {
            commentText = "🌧 Bussi, Baba! I bleib dahoam und schau Memes!";
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

                    // RANDOM MEME NEU GENERIEREN bei jedem Klick
                    String memePath = getRandomMeme(c);
                    if (memePath != null) {
                        popupController.setMemeImage(memePath);
                    }

                    Stage popup = new Stage();
                    popup.initOwner(commentButton.getScene().getWindow());
                    popup.setTitle("Wetter-Kommentar");
                    popup.setScene(new Scene(root));
                    popup.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } });

        // Hier wird das Wettericon ausgewählt
        String iconFile = getIconFile(condition);
        try {
            Image img = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("icons/" + iconFile))
            );
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

    @FXML
    private void onSoundToggle() {
        soundEnabled = !soundEnabled;
        updateSoundButtonUI();

        if (soundEnabled) {
            updateWeatherSound(lastCondition);
        } else {
            stopWeatherSound();
        }
    }

    private void updateSoundButtonUI() {
        if (soundEnabled) {
            soundButton.setText("🔊");
        } else {
            soundButton.setText("🔇");
        }
    }

    private void updateWeatherSound(String condition) {
        if (!soundEnabled) return;

        String soundFile = getSoundFile(condition);
        if (soundFile == null) {
            stopWeatherSound();
            return;
        }
        playWeatherSound(soundFile);
    }

    //  WAV mapping
    private String getSoundFile(String condition) {
        if (condition == null) return "cloud-sound.wav";
        String c = condition.trim().toLowerCase();

        if (c.contains("schnee"))   return "snow-sound.wav";
        if (c.contains("regen"))    return "rain-sound.wav";
        if (c.contains("gewitter")) return "thunder-sound.wav";
        if (c.contains("klar"))     return "sunny-sound.wav";
        if (c.contains("bewölkt"))  return "cloud-sound.wav";
        if (c.contains("nebel"))    return "fog-sound.wav";
        return "cloud-sound.wav";
    }

    private void playWeatherSound(String fileName) {
        stopWeatherSound();

        try {
            URL url = getClass().getResource("sounds/" + fileName);
            if (url == null) {
                System.err.println("Sound not found: " + fileName);
                return;
            }

            try (AudioInputStream ais =
                         AudioSystem.getAudioInputStream(new BufferedInputStream(url.openStream()))) {

                weatherClip = AudioSystem.getClip();
                weatherClip.open(ais);

                // volume optional
                if (weatherClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gain = (FloatControl) weatherClip.getControl(FloatControl.Type.MASTER_GAIN);
                    gain.setValue(-10.0f); // quieter
                }

                weatherClip.loop(Clip.LOOP_CONTINUOUSLY);
                weatherClip.start();
            }

        } catch (Exception e) {
            System.err.println("Could not play sound: " + fileName);
            e.printStackTrace();
            stopWeatherSound();
        }
    }

    private void stopWeatherSound() {
        try {
            if (weatherClip != null) {
                weatherClip.stop();
                weatherClip.close();
                weatherClip = null;
            }
        } catch (Exception ignored) {}
    }
    // Random  Auswahl von memes pro wetter
    private String getRandomMeme(String condition) {

        // Zur Hilfe bei der Erstellung wurden diese Seiten konsultiert:
        // https://docs.oracle.com/en/java/javase/23/core/creating-immutable-lists-sets-and-maps.html?utm_source=chatgpt.com
        // https://stackoverflow.com/questions/10928004/how-to-achieve-this-mapstring-list-structure
        //https://www.java-forum.org/thema/random-erzeugen.178586/


        Map<String, List<String>> memes = Map.of(

                // Schnee
                "schnee", List.of("/com/example/chaotixweatherapp/memes/schnee_meme1.png",   "/com/example/chaotixweatherapp/memes/schnee_meme2.png", "/com/example/chaotixweatherapp/memes/schnee_meme3.png", "/com/example/chaotixweatherapp/memes/schnee_meme4.png", "/com/example/chaotixweatherapp/memes/schnee_meme5.png", "/com/example/chaotixweatherapp/memes/schnee_meme6.png"),



                //Regen
                "regen", List.of("/com/example/chaotixweatherapp/memes/regen_meme1.png", "/com/example/chaotixweatherapp/memes/regen_meme2.png", "/com/example/chaotixweatherapp/memes/regen_meme3.png", "/com/example/chaotixweatherapp/memes/regen_meme4.png", "/com/example/chaotixweatherapp/memes/regen_meme5.png", "/com/example/chaotixweatherapp/memes/regen_meme6.png"),


                // Sonne, Klar
                "klar", List.of( "/com/example/chaotixweatherapp/memes/klar_meme1.png",   "/com/example/chaotixweatherapp/memes/klar_meme2.png", "/com/example/chaotixweatherapp/memes/klar_meme4.png", "/com/example/chaotixweatherapp/memes/klar_meme5.png", "/com/example/chaotixweatherapp/memes/klar_meme6.png", "/com/example/chaotixweatherapp/memes/klar_meme7.png"),

                //Gewitter
                "gewitter", List.of("/com/example/chaotixweatherapp/memes/gewitter_meme1.png", "/com/example/chaotixweatherapp/memes/gewitter_meme2.png"),


                // Nebel
                "nebel", List.of("/com/example/chaotixweatherapp/memes/nebel_meme1.png", "/com/example/chaotixweatherapp/memes/nebel_meme2.png","/com/example/chaotixweatherapp/memes/nebel_meme3.png", "/com/example/chaotixweatherapp/memes/nebel_meme4.png"),

                //Wolken
                "bewölkt", List.of("/com/example/chaotixweatherapp/memes/bewölkt_meme1.png","/com/example/chaotixweatherapp/memes/bewölkt_meme2.png","/com/example/chaotixweatherapp/memes/bewölkt_meme3.png","/com/example/chaotixweatherapp/memes/bewölkt_meme4.png"));




        for (String wetter : memes.keySet()) {
            if (condition.contains(wetter)) {
                List<String> list = memes.get(wetter);
                return list.get(new Random().nextInt(list.size()));
            }}return null;
    }

    // Zurück zur Startseite mit dem Back-Button
    @FXML
    private void onBackClick() throws IOException {
        stopWeatherSound();
        FXMLLoader loader = new FXMLLoader(
                WeatherApp.class.getResource("hello-view.fxml")
        );
        Parent root = loader.load();

        Stage stage = (Stage) headlineLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
