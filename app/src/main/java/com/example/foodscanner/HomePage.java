package com.example.foodscanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    Button search ;
    Button saved;
    RecyclerView history;
    RecyclerView favorites;
    Database database = new Database(this);
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    ImageButton close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);


        search = findViewById(R.id.scan);
        saved = findViewById(R.id.saved);
        history = findViewById(R.id.history);
        showProductsHistory();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBarScan();
            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav();
            }
        });

        /**
         saved.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        openList();
        }
        });*/

    }

    private void showProductsHistory() {
        Cursor data = database.getProductsHistory();
        ArrayList<Product> list = new ArrayList<>();
        while (data.moveToNext()) {
            Product product = new Product(data.getString(1), data.getString(2),
                    data.getDouble(3), data.getDouble(4), data.getDouble(5),
                    data.getDouble(6), data.getDouble(7), data.getDouble(8),data.getDouble(9),
                    data.getString(10),data.getString(11),data.getString(12));
            product.setId(data.getString(0));
            product.setHealthiness(data.getString(13));
            list.add(product);
        }
        MyAdapter myAdapter = new MyAdapter(HomePage.this, list, true);
        history.setAdapter(myAdapter);
        history.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showProductsFav() {
        Cursor data = database.getProductsFav();
        ArrayList<Product> list = new ArrayList<>();
        while (data.moveToNext()) {
            Product product = new Product(data.getString(1), data.getString(2),
                    data.getDouble(3), data.getDouble(4), data.getDouble(5),
                    data.getDouble(6), data.getDouble(7), data.getDouble(8),data.getDouble(9),
                    data.getString(10),data.getString(11),data.getString(12));
            product.setId(data.getString(0));
            product.setHealthiness(data.getString(13));
            list.add(product);
        }
        MyAdapter myAdapter = new MyAdapter(this, list, true);
        favorites.setAdapter(myAdapter);
        favorites.setLayoutManager(new LinearLayoutManager(this));
    }

    public void fav(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.favorites, null);
        close = popupView.findViewById(R.id.close);
        favorites =  popupView.findViewById(R.id.favorites);
        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();
        showProductsFav();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void openBarScan(){
        Intent barScan = new Intent(this, MainActivity.class);
        startActivity(barScan);
    }

    /**
     public void openList(){
     Intent saved = new Intent(this, savedItems.class);
     startActivity(saved);
     }*/
}