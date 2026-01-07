
package com.example.chaotixweatherapp;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;

/*
Diese Klasse dient für die Abrage an die OpenWeatherMap API, um die Daten zu bekommen.
ANhand des API keys kann man eine http Abfrage machen, und man bekommt eine JSON als Antwort
Es wird die JSON Datei mithilfe von GJSON interpretiert und in Variablen der Klasse gespeichert

Sobalt diese Klasse instanziert wird, muss ein Stnadort übergeben werden.
Mithilfe von methoden kann man nun auf die Datensätze zugreifen (getter Methoden)
 */
public class WeatherApi {

    private String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String API_KEY = "8d70c3c6ff67fc01f5fc58fb531d9e3b";
    private String location;
    private String actualLocation;
    private Double temp;
    private String description;
    private int weatherID;
    private String unit;

    private String testUrl; // Für Test zwecke

    //units = "Celsius" ODER "Fahrenheit"


    public WeatherApi(String location, String unit){
        this.location = location;

        // 1. URL zusammenstellen
        String encodedCityName = URLEncoder.encode(this.location, StandardCharsets.UTF_8);
        String finalUrl = String.format("%s?q=%s&appid=%s&units=%s",
                baseUrl, encodedCityName, API_KEY, "metric"); // "metric" --> Return in °C
        this.testUrl = finalUrl; //Testzwecke

        // 2. HTTP-Client und Request erstellen
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(finalUrl))
                .build();
        try {
            // 3. Request senden
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Statuscode prüfen
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();

                // 5. JSON-Response parsen
                Gson gson = new Gson();
                WeatherJson.WeatherToGet weatherData = gson.fromJson(jsonResponse, WeatherJson.WeatherToGet.class);

                //Temperatur Umrechnung °C in Fahrenheit, wenn nötig:
                if (unit == "Celsius"){
                    this.temp = weatherData.main.temp; //Weil Abfrage return in °C wegen "metric"
                }
                else {
                    this.temp = (weatherData.main.temp * (9/5) +32);
                }

                // 6. Restliche Ergebnisse in Variablen speichern:
                this.actualLocation = weatherData.name;
                this.description = weatherData.weather[0].description;
                this.weatherID = weatherData.weather[0].id;


            //Wenn Fehler auftritt wird dies Ausgegeben:
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

    public String getActualLocation() {
        return actualLocation;
    }

    public Double getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public int getWeatherID() {
        return weatherID;
    }

    public static void main(String[] args) {
        WeatherApi test = new WeatherApi("Klagenfurt", "Fahrenheit");

        System.out.println(test.getActualLocation());
        System.out.println(test.getTemp());
        System.out.println(test.getDescription());
    }
}

