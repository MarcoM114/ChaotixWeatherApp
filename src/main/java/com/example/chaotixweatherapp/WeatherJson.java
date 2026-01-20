package com.example.chaotixweatherapp;
// Hier werden die Datensätze definiert, die von der JSON Datei abgegriffen werden sollen
// Anhand von Doku GJSON

public class WeatherJson {

    // In Main befinden sich die benötigten Wetter Daten
    public class Main{
        double temp;
        int pressure;
        int humidity;
    }

    // Hier werden Werte innerhalb von Weather abgegriffen
    public class Weather{
        int id;
        String description;
    }

    // Dies ist die "oberste" Ebene wo abgegriffen wird.
    // Es wird Main ind Weather verwendet
    public class WeatherToGet{
        String name;
        Weather[] weather;
        Main main;
        /*
        Array ist bei weather notwendig, da dies im JSON auch so gefordert ist.
        Hintergrund:
        An einen Tag kann es zugleich verschiedene weathers geben, zB regen und nebel
        Standardmäßig werten wir [0] aus         */
    }
}
