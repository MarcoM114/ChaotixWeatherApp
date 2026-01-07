package com.example.chaotixweatherapp;
// Hier werden die Datensätze definiert, die von der JSON Datei abgegriffen werden sollen
// Anhand von Doku GJSON

public class WeatherJson {

    public class Main{
        double temp;
        int pressure;
        int humidity;
    }

    public class Weather{
        int id;
        String description;
    }

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
