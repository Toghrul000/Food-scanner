package com.example.foodscanner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    RecyclerView recyclerView;


    String s1[];
    String s2[];
    int images[];

    String url = "https://world.openfoodfacts.org/api/v0/product/0000000000.json";

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
        String barcode = intent.getStringExtra(MainActivity.barcode);
        url = "https://world.openfoodfacts.org/api/v0/product/"+ barcode + ".json";

        search = findViewById(R.id.scanA);
        home = findViewById(R.id.home);

        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView1);


//        s1 = getResources().getStringArray(R.array.nutritionalInfo);
//        s2 = getResources().getStringArray(R.array.description);

        new GetJSONTask().execute(url);

        MyAdapter myAdapter = new MyAdapter(this, s1,s2);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
        private String sugar;
        private String carbs;
        private String fat;
        private String salt;
        private String energy;
        private String sodium;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Results.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


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
            if (pDialog.isShowing())
                pDialog.dismiss();

            name = productJson.get("product_name").getAsString();
            imageUrl = productJson.get("image_front_url").getAsString();
            JsonObject nutrients = productJson.getAsJsonObject("nutriments");
            carbs = nutrients.get("carbohydrates").getAsString();
            sugar = nutrients.get("sugars").getAsString();
            fat = nutrients.get("fat").getAsString();
            salt = nutrients.get("salt").getAsString();
            energy = nutrients.get("energy").getAsString();
            sodium = nutrients.get("sodium").getAsString();

            textView.setText(name);
            s1 = new String[8];
            s2 = new String[8];



//
//            s1[0] = carbsI;
//            s2[0] = sugarI;











        }
    }



}
