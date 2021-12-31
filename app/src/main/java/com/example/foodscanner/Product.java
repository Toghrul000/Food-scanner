package com.example.foodscanner;



import com.google.gson.JsonArray;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getSalt() {
        return salt;
    }

    public void setSalt(double salt) {
        this.salt = salt;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }


}
