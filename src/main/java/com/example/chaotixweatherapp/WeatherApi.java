
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

    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String API_KEY = "8d70c3c6ff67fc01f5fc58fb531d9e3b";
    private String location;
    private String actualLocation;
    private Double temp;
    private String description;
    private int weatherID;
    private String unit;
    private final String[] dataW = new String[2]; //return wert der Methode

    private String testUrl; // Für Test zwecke

    //units = "Celsius" ODER "Fahrenheit"

    public WeatherApi() {
    }

    /*
    Wenn man diese Methode aufruft, wird eine Abfrage über die openweathermap API gemacht

    Man muss den Standort und die Einheit ("Celsius" oder "Fahrenheit") übergeben.

    Man bekommt ein String Array zurück, der folgendermaßen aussieht:
    String[0] = Temperatur
    String[1] = Wetterbeschreibung

    Bei einem Fehler sieht derr Array folgendermaßen aus:
    String[0] = "FEHLER"
    String[1] = Fehlermeldung
     */
    public String[] getWeatherData(String location, String unit){
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
                if (unit.equals("Celsius")){
                    this.temp = weatherData.main.temp; //Weil Abfrage return in °C wegen "metric"
                }
                else {
                    this.temp = (weatherData.main.temp * (9.0/5.0) +32);
                }

                // 6. Restliche Ergebnisse in Variablen speichern:
                this.actualLocation = weatherData.name;
                this.description = weatherData.weather[0].description;
                this.weatherID = weatherData.weather[0].id;
                System.out.println(this.weatherID);

                this.dataW[0] = Double.toString(this.temp);  //Temperatur wird in Array an erster Stelle gespeichert

                // 7. Icon Zuordnung und Beschreibung:

                int weatherIDFirst = weatherID / 100;

                if (weatherIDFirst == 2){
                    this.description = "Gewitter";
                } else if (weatherIDFirst == 3 || weatherIDFirst == 5) {
                    this.description = "Regen";
                } else if (weatherIDFirst == 6) {
                    this.description = "Schnee";
                } else if (weatherIDFirst == 7) {
                    this.description = "Nebel";
                } else if (weatherID == 800) {
                    this.description = "Klar";
                } else if (weatherID >800) {
                    this.description = "Bewölkt";
                }else {
                    this.description = "No Icon";
                }
                this.dataW[1] = this.description; // Wird in Array gespeichert


                //Wenn Fehler auftritt wird dies Ausgegeben:
            } else {
                System.err.println("Fehler bei der API-Abfrage. Statuscode: " + response.statusCode());
                System.err.println("   Meldung: " + response.body());
                System.err.println("   Stellen Sie sicher, dass Ihr API-Key korrekt ist und der Ort existiert.");

                dataW[0] = "FEHLER";
                if (response.statusCode() == 404){
                    dataW[1] = "Name des Standortes nicht bekannt!";
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Fehler beim Senden der HTTP-Anfrage: " + e.getMessage());

            dataW[0] = "FEHLER";
            dataW[1] = "Server konnte nicht erreicht werden";
        }

        return this.dataW; //Gibt die Wetterdaten zurück als Array von Strings
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

    /*
    public static void main(String[] args) {

        WeatherApi test = new WeatherApi();

        String[] data;

        data = test.getWeatherData("Wien", "Celsius");

        System.out.println(data[0]);
        System.out.println(data[1]);



    }

     */
}

