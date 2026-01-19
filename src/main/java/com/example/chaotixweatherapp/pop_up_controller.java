package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class pop_up_controller {

    @FXML
    private Label commentLabel;

    @FXML
    private ImageView memeImage;

    @FXML
    private Button closeButton;


    //Wenn der Benutzer auf „Schließen“ klickt → Fenster schließen, Hilfestellung von hier: https://www.reddit.com/r/javahelp/comments/nn09qw/cant_use_getscenegetwindow_in_javafx_why/
    @FXML
    public void initialize() {
        closeButton.setOnAction(e -> ((Stage) closeButton.getScene().getWindow()).close());
    }

    // diese Methode setzt den Text im PopUP
    public void setCommentText(String text) {
        commentLabel.setText(text);
    }


    // diese Methode setzt das Bild im PopUP

    public void setMemeImage(String imagePath) {

        // das Bild wird aus dem Projektordner geladen, der Pfad imagePath wird übergeben
        // das Bild wird dann im PopUP angezeigt
        memeImage.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(imagePath))
        ));
    }



}
