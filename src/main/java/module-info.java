module com.example.chaotixweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.net.http;
    requires com.google.gson;
    requires java.desktop;


    opens com.example.chaotixweatherapp to javafx.fxml, com.google.gson;

    exports com.example.chaotixweatherapp;
}
