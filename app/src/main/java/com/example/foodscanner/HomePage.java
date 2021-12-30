package com.example.foodscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    Button search ;
    Button saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        search = findViewById(R.id.scan);
        saved = findViewById(R.id.saved);

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