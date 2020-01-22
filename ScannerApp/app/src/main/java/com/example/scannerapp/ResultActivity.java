package com.example.scannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ResultActivity extends AppCompatActivity {

    public  static TextView nameTextView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_results_home);
        nameTextView = findViewById(R.id.machine_name_view);



        CardView card6 = findViewById(R.id.card_prevNode1);
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Ar.class);
                startActivity(intent);

            }
        });

        CardView card7 = findViewById(R.id.card_nextNode1);
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Ar.class);
                startActivity(intent);

            }
        });
    }
}
