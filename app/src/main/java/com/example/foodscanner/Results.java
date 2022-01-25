package com.example.foodscanner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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





    String[] breadUrls = {"https://nl.openfoodfacts.org/categorie/broden/1.json", "https://nl.openfoodfacts.org/categorie/broden/2.json", "https://nl.openfoodfacts.org/categorie/broden/3.json", "https://nl.openfoodfacts.org/categorie/broden/4.json",
            "https://nl.openfoodfacts.org/categorie/broden/5.json", "https://nl.openfoodfacts.org/categorie/broden/6.json",
            "https://nl.openfoodfacts.org/categorie/broden/7.json", "https://nl.openfoodfacts.org/categorie/broden/8.json",
            "https://nl.openfoodfacts.org/categorie/broden/9.json"};

    String[] sodaUrls = {"https://nl.openfoodfacts.org/categorie/frisdranken/1.json", "https://nl.openfoodfacts.org/categorie/frisdranken/2.json", "https://nl.openfoodfacts.org/categorie/frisdranken/3.json"};

    String[] cerealUrls = {"https://nl.openfoodfacts.org/categorie/ontbijtgranen/1.json", "https://nl.openfoodfacts.org/categorie/ontbijtgranen/2.json", "https://nl.openfoodfacts.org/categorie/ontbijtgranen/3.json", "https://nl.openfoodfacts.org/categorie/ontbijtgranen/4.json",
            "https://nl.openfoodfacts.org/categorie/ontbijtgranen/5.json", "https://nl.openfoodfacts.org/categorie/ontbijtgranen/6.json"};

    Button search ;
    Button home;
    TextView textView;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView history;
    List<Product> products;

    List<Product> relatedProducts = new ArrayList<>();

    List<Product> ProductsHealthy = new ArrayList<>();
    List<Product> ProductsNeutral = new ArrayList<>();
    List<Product> ProductsUnHealthy = new ArrayList<>();

    Database database = new Database(this);

    String barcode;

    FoodClassifier foodClassifier = null;





    String url = "https://world.openfoodfacts.org/api/v0/product/0000000000.json";

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
//        barcode = intent.getStringExtra(MainActivity.barcode);
        barcode = intent.getExtras().getString("Pbarcode");
        url = "https://world.openfoodfacts.org/api/v0/product/"+ barcode + ".json";

        search = findViewById(R.id.scanA);
        home = findViewById(R.id.home);

        textView = findViewById(R.id.textView);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);
        history = findViewById(R.id.history);
        try {
            foodClassifier = new FoodClassifier(Results.this);
        } catch (Exception e) {
            e.printStackTrace();
        }


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
//            product.setId(Integer.valueOf(barcode));
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

        public void AddProduct(Product product) {
            boolean insertData = database.addProductHistory(product);
        }


        protected void onPostExecute(Product product) {

            String[] details = new String[2];


            try {
                details = foodClassifier.predictHealthiness(product);
                product.setHealthiness(details[1]);

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (pDialog.isShowing())
                pDialog.dismiss();





//            textView.setText(product.getName());

            //Big textview scanned product======
            textView.setText(product.getHealthiness());
            if (product.getHealthiness().equals("Unhealthy")){
                textView.setTextColor(Color.RED);
            } else if (product.getHealthiness().equals("Healthy")){
                textView.setTextColor(Color.GREEN);
            } else if (product.getHealthiness().equals("Neutral")){
                textView.setTextColor(Color.YELLOW);
            }
            //===================================

            products = new ArrayList<>();
            if(products.size() != 0){
                products.set(0, product);
            } else {
                products.add(product);
            }

            AddProduct(product);



            MyAdapter myAdapter = new MyAdapter(Results.this, products,true);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Results.this));



            try {

                product.setFoodType(details[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String categorie = product.getFoodType();


            if(categorie.equals("Bread")){
                new GetRelated().execute(breadUrls[0], breadUrls[1], breadUrls[2],
                        breadUrls[3], breadUrls[4], breadUrls[5], breadUrls[6], breadUrls[7], breadUrls[8]);

            } else if(categorie.equals("Softdrink")){
                new GetRelated().execute(sodaUrls[0], sodaUrls[1], sodaUrls[2]);

            } else if(categorie.equals("Cereals")){
                new GetRelated().execute(cerealUrls[0], cerealUrls[1], cerealUrls[2], cerealUrls[3], cerealUrls[4], cerealUrls[5]);
            }


//            for (int i = 0; i < products.get(0).getCategories().size(); i++) {
//                if(products.get(0).getCategories().get(i).getAsString().equals("en:breads")){
//                    new GetRelated().execute(breadUrls[0], breadUrls[1], breadUrls[2],
//                            breadUrls[3], breadUrls[4], breadUrls[5], breadUrls[6], breadUrls[7], breadUrls[8]);
//
//                    break;
//
//                } else if (products.get(0).getCategories().get(i).getAsString().equals("en:sodas")){
//                    new GetRelated().execute(sodaUrls[0], sodaUrls[1], sodaUrls[2]);
//
//                    break;
//
//                } else if(products.get(0).getCategories().get(i).getAsString().equals("en:cereals-and-their-products") ||
//                        products.get(0).getCategories().get(i).getAsString().equals("en:breakfast-cereals")){
//
//                    new GetRelated().execute(cerealUrls[0], cerealUrls[1], cerealUrls[2], cerealUrls[3], cerealUrls[4], cerealUrls[5]);
//                    break;
//
//                }
//
//            }



        }
    }






    class GetRelated extends AsyncTask<String, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(String... urls) {

            for (int i = 0; i < urls.length; i++) {
                category(urls[i]);
            }

            if (ProductsHealthy.size() != 0){
                for(Product i: ProductsHealthy){
                    relatedProducts.add(i);
                }
            }

            if (ProductsNeutral.size() != 0){
                for(Product i: ProductsNeutral){
                    relatedProducts.add(i);
                }
            }

            if (ProductsUnHealthy.size() != 0){
                for(Product i: ProductsUnHealthy){
                    relatedProducts.add(i);
                }
            }

            return relatedProducts;
        }

        public void category(String url){
            JsonObject rootJson = getJsonObj(url);
            JsonArray productsJson = rootJson.getAsJsonArray("products");
            for (int i = 0; i < productsJson.size(); i++) {
                Product p = new Product(productsJson.get(i).getAsJsonObject());
                if(p.getId().equals("noid") || p.getSugar() == -1 || p.getCarbs() == -1 || p.getEnergy() == -1 || p.getFat() == -1 || p.getSalt() == -1
                        || p.getSodium() == -1 || p.getProteins() == -1 || p.getFiber() == -1 || p.getSaturatedFat() == -1
                        || p.getImageUrl().equals("no") || p.getName().equals("noname")){

                }else {

                    //CHANGE TO HEALTHY

                    String[] details2 = new String[2];
                    try {
                        details2 = foodClassifier.predictHealthiness(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        p.setHealthiness(details2[1]);
                        p.setFoodType(details2[0]);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //SORTING by health
                    if (p.getHealthiness().equals("Unhealthy")){
                        ProductsUnHealthy.add(p);
                    } else if (p.getHealthiness().equals("Healthy")){
                        ProductsHealthy.add(p);
                    } else if (p.getHealthiness().equals("Neutral")){
                        ProductsNeutral.add(p);
                    }





//                    relatedProducts.add(p);

//                    if(p.getHealthiness().equals("Healthy") || p.getHealthiness().equals("Neutral")){
//                        relatedProducts.add(p);
//                    }

//                    if(products.get(0).getSugar() > p.getSugar() && products.get(0).getCarbs() > p.getCarbs()){
//                        relatedProducts.add(p);
//                    }
                }
            }
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



        protected void onPostExecute(List<Product> productsList) {
            if (pDialog.isShowing())
                pDialog.dismiss();


            MyAdapter myAdapter2 = new MyAdapter(Results.this, productsList,true);
            recyclerView2.setAdapter(myAdapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(Results.this));



        }
    }



}
