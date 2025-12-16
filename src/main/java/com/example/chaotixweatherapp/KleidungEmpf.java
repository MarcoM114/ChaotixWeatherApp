package com.example.chaotixweatherapp;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class KleidungEmpf {

    private static final String API_URL = "https://www.siemens.com/llm/v01"; // Hier die korrekte URL einfügen
    private static final String API_KEY = "SIAK-tNRUFrQ73dwc5Du9FUlIU2Hr290da095a"; // API-Schlüssel

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    // Klassen für die Datenstruktur der Anfrage
    record Message(String role, String content) {}
    record ChatRequest(String model, Message[] messages) {}

    // Klassen für die Datenstruktur der Antwort
    record Choice(Message message) {}
    record ChatResponse(Choice[] choices) {}

    public String sendPrompt(String userPrompt, String modelName) throws IOException, InterruptedException {
        // 1. JSON-Anfrage-Objekt erstellen
        Message systemMsg = new Message("system", "Du bist ein hilfsbereiter Assistent, der kurz und präzise antwortet.");
        Message userMsg = new Message("user", userPrompt);

        ChatRequest requestBody = new ChatRequest(modelName, new Message[]{systemMsg, userMsg});
        String jsonBody = gson.toJson(requestBody);

        // 2. HttpRequest erstellen
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                // API-Key zur Authentifizierung
                .header("Authorization", "Bearer " + API_KEY)
                // Wichtig: Content-Type auf JSON setzen
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // 3. Anfrage senden und Antwort empfangen
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 4. Statuscode prüfen
        if (response.statusCode() != 200) {
            throw new RuntimeException("API-Fehler: Statuscode " + response.statusCode() + ", Body: " + response.body());
        }

        // 5. JSON-Antwort verarbeiten und Ergebnis zurückgeben
        ChatResponse chatResponse = gson.fromJson(response.body(), ChatResponse.class);

        // Die erste Antwort-Nachricht extrahieren
        if (chatResponse.choices().length > 0) {
            return chatResponse.choices()[0].message().content();
        } else {
            return "Keine Antwort erhalten.";
        }
    }

    public static void main(String[] args) {
        KleidungEmpf apiClient = new KleidungEmpf();
        String prompt = "Was ist die Hauptstadt von Österreich?";
        String model = "mistral-7b-instruct"; // Ersetzen Sie dies durch das tatsächliche Modell

        try {
            String response = apiClient.sendPrompt(prompt, model);
            System.out.println("Prompt: " + prompt);
            System.out.println("Antwort des LLM: " + response);
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}