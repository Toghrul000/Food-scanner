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



    private JsonArray categories;
    private JsonArray keywords;


    private String name = "noname";
    private String imageUrl = "no";
    private double sugar = -1;
    private double carbs = -1;
    private double fat = -1;
    private double salt = -1;
    private double energy = -1;
    private double sodium = -1;

    private String fatL = "";
    private String saltL = "";
    private String sugarsL = "";

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
        if(productJson.has("product_name")){
            name = productJson.get("product_name").getAsString();
        }
        if(productJson.has("image_front_url")){
            imageUrl = productJson.get("image_front_url").getAsString();
        }

        categories = productJson.getAsJsonArray("categories_hierarchy");
        keywords = productJson.getAsJsonArray("_keywords");



        JsonObject nutrients = productJson.getAsJsonObject("nutriments");
        if (nutrients.has("carbohydrates")) {carbs = nutrients.get("carbohydrates").getAsDouble();}
        if (nutrients.has("sugars")) {sugar = nutrients.get("sugars").getAsDouble();}
        if (nutrients.has("fat")) {fat = nutrients.get("fat").getAsDouble();}
        if (nutrients.has("salt")) {salt = nutrients.get("salt").getAsDouble();}
        if (nutrients.has("energy")) {energy = nutrients.get("energy").getAsDouble();}
        if (nutrients.has("sodium")) {sodium = nutrients.get("sodium").getAsDouble();}

        JsonObject levels = productJson.getAsJsonObject("nutrient_levels");
        if (levels.has("fat")) {fatL = levels.get("fat").getAsString();}
        if (levels.has("salt")) {saltL= levels.get("salt").getAsString();}
        if (levels.has("sugars")) {sugarsL = levels.get("sugars").getAsString();}

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

    public JsonArray getCategories() {
        return categories;
    }

    public void setCategories(JsonArray categories) {
        this.categories = categories;
    }

    public JsonArray getKeywords() {
        return keywords;
    }

    public void setKeywords(JsonArray keywords) {
        this.keywords = keywords;
    }

    public Product(JsonObject productJson){
        if(productJson.has("product_name")){
            name = productJson.get("product_name").getAsString();
        }
        if(productJson.has("image_front_url")){
            imageUrl = productJson.get("image_front_url").getAsString();
        }
        JsonObject nutrients = productJson.getAsJsonObject("nutriments");
        if (nutrients.has("carbohydrates")) {carbs = nutrients.get("carbohydrates").getAsDouble();}
        if (nutrients.has("sugars")) {sugar = nutrients.get("sugars").getAsDouble();}
        if (nutrients.has("fat")) {fat = nutrients.get("fat").getAsDouble();}
        if (nutrients.has("salt")) {salt = nutrients.get("salt").getAsDouble();}
        if (nutrients.has("energy")) {energy = nutrients.get("energy").getAsDouble();}
        if (nutrients.has("sodium")) {sodium = nutrients.get("sodium").getAsDouble();}

        categories = productJson.getAsJsonArray("categories_hierarchy");
        keywords = productJson.getAsJsonArray("_keywords");

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

    public String getFatL() {
        if(fatL.equalsIgnoreCase("low")){
            return "#F841A842";
        }else if(fatL.equalsIgnoreCase("moderate")){
            return "#";
        }else if(fatL.equalsIgnoreCase("high")){
            return"#F8EF5A5A";
        }else{return "#FFFFFFFF"; }
    }

    public String getSaltL() {
        if(saltL.equalsIgnoreCase("low")){
            return "#F841A842";
        }else if(saltL.equalsIgnoreCase("moderate")){
            return "#FF6C692B";
        }else if(saltL.equalsIgnoreCase("high")){
            return"#F8EF5A5A";
        }else{return "#FFFFFFFF"; }

    }

    public String getSugarsL() {
        if(sugarsL.equalsIgnoreCase("low")){
            return "#F841A842";
        }else if(sugarsL.equalsIgnoreCase("moderate")){
            return "#FF6C692B";
        }else if(sugarsL.equalsIgnoreCase("high")){
            return"#F8EF5A5A";
        }else{return "#FFFFFFFF"; }
    }
}
