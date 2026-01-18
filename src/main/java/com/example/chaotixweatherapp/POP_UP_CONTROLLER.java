package com.example.chaotixweatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class POP_UP_CONTROLLER {

    @FXML
    private Label commentLabel;

    @FXML
    private ImageView memeImage;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        closeButton.setOnAction(e -> ((Stage) closeButton.getScene().getWindow()).close());
    }

    public void setCommentText(String text) {
        commentLabel.setText(text);
    }

    public void setMemeImage(String imagePath) {
        try {
            memeImage.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(imagePath))
            ));
        } catch (Exception e) {
            System.err.println("Meme not found: " + imagePath);
        }
    }
}
