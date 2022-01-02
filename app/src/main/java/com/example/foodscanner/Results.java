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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    Button search ;
    Button home;
    TextView textView;
    RecyclerView recyclerView;
    RecyclerView history;
    List<Product> products;
    List<Product> hproducts;

    List<Product> relatedProducts;



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
        history = findViewById(R.id.history);



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




    class GetJSONTask extends AsyncTask<String, Void, Product> {

        @Override
        protected Product doInBackground(String... urls) {
            Product product = new Product(urls[0]);
            return product;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Results.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        protected void onPostExecute(Product product) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            textView.setText(product.getName());

            products = new ArrayList<>();
            if(products.size() != 0){
                products.set(0, product);
            } else {
                products.add(product);
            }


            MyAdapter myAdapter = new MyAdapter(Results.this, products);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Results.this));

            hproducts = new ArrayList<>();
            hproducts.add(product);

//
//            MyAdapter historyAdapter = new MyAdapter(Results.this, products);
//            history.setAdapter(historyAdapter);
//            history.setLayoutManager(new LinearLayoutManager(Results.this));

        }
    }



    class GetRelated extends AsyncTask<String, Void, Product> {

        @Override
        protected Product doInBackground(String... urls) {

            return null;
        }

        public void category(String url){
            JsonObject rootJson = getJsonObj(url);
            JsonArray productsJson = rootJson.getAsJsonArray("product");


        }


        public JsonObject getJsonObj(String url){
            JsonObject rootJson = new JsonObject();
            URL jsonURL = null;

            try {
                jsonURL = new URL(url);
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
            return rootJson;

        }






        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Results.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        protected void onPostExecute(Product product) {
            if (pDialog.isShowing())
                pDialog.dismiss();


        }
    }



}
