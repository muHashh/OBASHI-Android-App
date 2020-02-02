package com.example.scannerapp.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import com.example.scannerapp.R;
import com.example.scannerapp.ui.ar.ArActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    public static TextView nameTextView;
    public static TextView info_text;
    private String result;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_results_home);
        result = HomeFragment.result;
        FetchDeviceAsyncTask connectMySql = new FetchDeviceAsyncTask();
        connectMySql.execute(result);
        nameTextView = findViewById(R.id.machine_name_view);
        info_text = findViewById(R.id.info_text1);

        Intent intent = this.getIntent();

        if(intent !=null) {
            String name = intent.getExtras().getString("NAME");
            ResultActivity.nameTextView.setText(name);
            ResultActivity.info_text.setText("desc.");
        } else {
            ResultActivity.nameTextView.setText("Error");
        }


        CardView card6 = findViewById(R.id.card_prevNode1);
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArActivity.class);
                startActivity(intent);

            }
        });

        CardView card7 = findViewById(R.id.card_nextNode1);
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArActivity.class);
                startActivity(intent);

            }
        });
    }
}
