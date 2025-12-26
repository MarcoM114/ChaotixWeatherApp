module com.example.chaotixweatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.chaotixweatherapp to javafx.fxml;
    exports com.example.chaotixweatherapp;
}
