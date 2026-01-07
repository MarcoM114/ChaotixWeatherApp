module com.example.chaotixweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.chaotixweatherapp to javafx.fxml;
    exports com.example.chaotixweatherapp;
}
