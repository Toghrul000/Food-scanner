package com.example.foodscanner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomePage extends AppCompatActivity {

    Button search ;
    Button saved;
    RecyclerView history;
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);


        search = findViewById(R.id.scan);
        saved = findViewById(R.id.saved);
        history = findViewById(R.id.history);
        showProducts();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBarScan();
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

    private void showProducts() {
        Cursor data = database.getProduct();
        ArrayList<Product> list = new ArrayList<>();
        Product p1 = new Product("jljl", "h", 9000, 90000, 300004, 3,3,3);
        list.add(p1);
        while (data.moveToNext()) {
            Product product = new Product(data.getString(1), data.getString(2),
                    data.getDouble(3), data.getDouble(4), data.getDouble(5),
                    data.getDouble(6), data.getDouble(7), data.getDouble(8));
            list.add(product);
        }
        MyAdapter myAdapter = new MyAdapter(HomePage.this, list);
        history.setAdapter(myAdapter);
        history.setLayoutManager(new LinearLayoutManager(this));
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