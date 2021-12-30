package com.example.foodscanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddata);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.barcode);

        TextView textView = findViewById(R.id.tv_textview2);
        textView.setText(message);


    }

}
