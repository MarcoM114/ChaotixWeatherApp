package com.example.chaotixweatherapp;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson; // Benötigt die Gson-Abhängigkeit!

public class WeatherApi {

    //WICHTIG: Ersetzen Sie dies durch Ihren OpenWeatherMap API Key
    private static final String API_KEY = "8d70c3c6ff67fc01f5fc58fb531d9e3b";

    // Standort, für den Sie das Wetter abrufen möchten
    private static final String CITY_NAME = "Berlin";

    // Basiskonstanten
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String UNITS = "metric"; // Für Celsius

    public static void main(String[] args) {

        // 1. URL zusammenstellen
        String encodedCityName = URLEncoder.encode(CITY_NAME, StandardCharsets.UTF_8);
        String finalUrl = String.format("%s?q=%s&appid=%s&units=%s",
                BASE_URL, encodedCityName, API_KEY, UNITS);

        // 2. HTTP-Client und Request erstellen
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(finalUrl))
                .build();

        try {
            // 3. Request senden (synchron)
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Statuscode prüfen
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();

                // 5. JSON-Response parsen
                Gson gson = new Gson();
                WeatherResponse weatherData = gson.fromJson(jsonResponse, WeatherResponse.class);

                // 6. Ergebnisse ausgeben
                System.out.println("✅ Wetterdaten erfolgreich für: " + weatherData.name);
                System.out.println("   Temperatur: " + weatherData.main.temp + " °C");
                System.out.println("   Gefühlt: " + weatherData.main.feels_like + " °C");

                if (weatherData.weather.length > 0) {
                    System.out.println("   Wetter: " + weatherData.weather[0].description);
                }

            } else {
                System.err.println("❌ Fehler bei der API-Abfrage. Statuscode: " + response.statusCode());
                System.err.println("   Meldung: " + response.body());
                System.err.println("   Stellen Sie sicher, dass Ihr API-Key korrekt ist und der Ort existiert.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("❌ Fehler beim Senden der HTTP-Anfrage: " + e.getMessage());
        }
    }
}
// Models für die JSON-Antwort von OpenWeatherMap

// Definiert die Hauptstruktur der Antwort
class WeatherResponse {
    String name;          // Name des Ortes
    Main main;            // Enthält Temperaturdaten
    Weather[] weather;    // Enthält die Wetterbeschreibung (als Array)
}

// Definiert die Wetterdetails (z.B. "description")
class Weather {
    String description;
    // int id; // Könnte für Wettersymbole verwendet werden
}

// Definiert die wichtigsten Temperatur- und Druckwerte
class Main {
    double temp;
    double feels_like; // Gefühlte Temperatur
    // double temp_min;
    // double temp_max;
    // int pressure;
    // int humidity;
}

// Wichtig: In der Realität würden Sie diese Klassen
// in separate .java-Dateien legen, z.B. Main.java, Weather.java etc.