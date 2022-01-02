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
    String s3[];
    String s4[];
    String s5[];
    String s6[];

    String images[];

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
        protected Product doInBackground(String... urls) {
            Product product = new Product(urls[0]);
            return product;
        }



        protected void onPostExecute(Product product) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            name = product.getName();
            carbs = product.getCarbs() + "";
            sugar = product.getSugar() + "";

            fat = product.getFat() + "";
            salt = product.getSalt() + "";
            energy = product.getEnergy() + "";
            sodium = product.getSodium() + "";



            textView.setText(name);
            s1 = new String[1];
            s2 = new String[1];
            s3 = new String[1];
            s4 = new String[1];
            s5 = new String[1];
            s6 = new String[1];
            images = new String[1];



            s1[0] = "SUGAR: " + sugar + "g";
            s2[0] = "CARBS: " + carbs + "g";
            s3[0] = "FAT: " + fat + "g";
            s4[0] = "SALT: " + salt + "g";
            s5[0] = "ENERGY: " + energy + "kJ";
            s6[0] = "SODIUM: " + sodium + "g";
            images[0] = product.getImageUrl();



            MyAdapter myAdapter = new MyAdapter(Results.this, s1,s2,s3,s4,s5,s6,images);

            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Results.this));






        }
    }



}
