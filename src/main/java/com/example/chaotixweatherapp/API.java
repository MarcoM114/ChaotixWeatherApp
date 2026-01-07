package com.example.chaotixweatherapp;

public class API {

    public WeatherData getWeather(String city) {
        // Dummy data (replace later with real API call)
        return switch (city) {
            case "Wien"   -> new WeatherData(-5, "Es schneit", "snow");
            case "Berlin" -> new WeatherData(2, "Bewölkt", "cloud");
            case "Zürich" -> new WeatherData(0, "Leichter Schneefall", "snow");
            default       -> new WeatherData(10, "Unbekannt", "cloud");
        };
    }
}