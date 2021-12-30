package com.example.foodscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {

    Button search ;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.barcode);

        search = findViewById(R.id.scanA);
        home = findViewById(R.id.home);

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


}
