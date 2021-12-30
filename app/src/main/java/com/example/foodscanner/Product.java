package com.example.foodscanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Product {
    private String name;
    private String imageUrl;
    private double sugar;
    private double carbs;
    private double fat;
    private double salt;
    private double energy;
    private double sodium;

    public Product(String jsonLink) {
        //initialize the product automatically based on the url to the json
        JsonObject rootJson = new JsonObject();
        URL jsonURL = null;

        try {
            jsonURL = new URL(jsonLink);
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            assert jsonURL != null;
            URLConnection request = jsonURL.openConnection();
            request.connect();
            JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
            rootJson = jsonElement.getAsJsonObject();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }

        JsonObject productJson = rootJson.getAsJsonObject("product");
        name = productJson.get("product_name").getAsString();
        imageUrl = productJson.get("image_front_url").getAsString();
        JsonObject nutrients = productJson.getAsJsonObject("nutriments");
        carbs = nutrients.get("carbohydrates").getAsDouble();
        sugar = nutrients.get("sugars").getAsDouble();
        fat = nutrients.get("fat").getAsDouble();
        salt = nutrients.get("salt").getAsDouble();
        energy = nutrients.get("energy").getAsDouble();
        sodium = nutrients.get("sodium").getAsDouble();
    }

    public Product(String name, String imageUrl, double sugar, double carbs, double fat, double salt, double energy, double sodium) {
        //initialize product manually
        this.name = name;
        this.imageUrl = imageUrl;
        this.sugar = sugar;
        this.carbs = carbs;
        this.fat = fat;
        this.salt = salt;
        this.energy = energy;
        this.sodium = sodium;
    }

}
