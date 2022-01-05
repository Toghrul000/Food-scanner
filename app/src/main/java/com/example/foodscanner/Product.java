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
    private double proteins = -1;

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    private String foodType = "no data";

    public String getHealthiness() {
        return healthiness;
    }

    public void setHealthiness(String healthiness) {
        this.healthiness = healthiness;
    }

    private String healthiness = "no data";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id = "noid";

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    private double fiber = -1;
    private double saturatedFat = -1;

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }



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
        if(productJson.has("code")){
            id = productJson.get("code").getAsString();

        }
        if (nutrients.has("carbohydrates")) {carbs = nutrients.get("carbohydrates").getAsDouble();}
        if (nutrients.has("sugars")) {sugar = nutrients.get("sugars").getAsDouble();}
        if (nutrients.has("fat")) {fat = nutrients.get("fat").getAsDouble();}
        if (nutrients.has("salt")) {salt = nutrients.get("salt").getAsDouble();}
        if (nutrients.has("energy")) {energy = nutrients.get("energy").getAsDouble();}
        if (nutrients.has("sodium")) {sodium = nutrients.get("sodium").getAsDouble();}
        if (nutrients.has("proteins")) {proteins = nutrients.get("proteins").getAsDouble();}
        if (nutrients.has("fiber")) {fiber = nutrients.get("fiber").getAsDouble();}
        if (nutrients.has("saturated-fat")) {saturatedFat = nutrients.get("saturated-fat").getAsDouble();}


    }

    public Product(String name, String imageUrl, double sugar, double carbs, double fat, double salt, double energy, double sodium, double proteins) {
        //initialize product manually
        this.name = name;
        this.imageUrl = imageUrl;
        this.sugar = sugar;
        this.carbs = carbs;
        this.fat = fat;
        this.salt = salt;
        this.energy = energy;
        this.sodium = sodium;
        this.proteins = proteins;
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
        if(productJson.has("code")){
            id = productJson.get("code").getAsString();

        }
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
        if (nutrients.has("proteins")) {proteins = nutrients.get("proteins").getAsDouble();}
        if (nutrients.has("fiber")) {fiber = nutrients.get("fiber").getAsDouble();}
        if (nutrients.has("saturated-fat")) {saturatedFat = nutrients.get("saturated-fat").getAsDouble();}

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


}
