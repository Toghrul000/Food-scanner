package com.example.foodscanner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Results extends AppCompatActivity {

    Button search ;
    Button home;
    TextView textView;

    String url = "https://world.openfoodfacts.org/api/v0/product/23073159.json";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.barcode);

        search = findViewById(R.id.scanA);
        home = findViewById(R.id.home);

        textView = findViewById(R.id.textView);

        new GetJSONTask().execute(url);






        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScanBar();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });

    }





    public void openScanBar(){
        Intent barScan = new Intent(this, MainActivity.class);
        startActivity(barScan);
    }
    public void openHomePage(){
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }




    class GetJSONTask extends AsyncTask<String, Void, JsonObject> {

        private String name;
        private String imageUrl;
        private double sugar;
        private double carbs;
        private double fat;
        private double salt;
        private double energy;
        private double sodium;


        @Override
        protected JsonObject doInBackground(String... urls) {

            JsonObject rootJson = new JsonObject();
            URL jsonURL = null;

            try {
                jsonURL = new URL(urls[0]);
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



            return productJson;
        }



        protected void onPostExecute(JsonObject productJson) {
            name = productJson.get("product_name").getAsString();
            imageUrl = productJson.get("image_front_url").getAsString();
            JsonObject nutrients = productJson.getAsJsonObject("nutriments");
            carbs = nutrients.get("carbohydrates").getAsDouble();
            sugar = nutrients.get("sugars").getAsDouble();
            fat = nutrients.get("fat").getAsDouble();
            salt = nutrients.get("salt").getAsDouble();
            energy = nutrients.get("energy").getAsDouble();
            sodium = nutrients.get("sodium").getAsDouble();

            textView.setText(name);






        }
    }



}
