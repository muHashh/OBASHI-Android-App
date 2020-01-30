package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class CardOpenActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        setContentView(i.getIntExtra("LAYOUT_NAME", 1));
    }
}
