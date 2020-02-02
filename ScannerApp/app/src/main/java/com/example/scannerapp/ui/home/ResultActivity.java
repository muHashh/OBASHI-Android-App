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

    public  static TextView nameTextView;
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

    private static class FetchDeviceAsyncTask extends AsyncTask<String, String, String[]> {

        String[] details = new String[3];

        @Override
        protected String[] doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("DeviceID", params[0]);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "fetch_device_details.php", "GET", parameters);
            try {
                // Gets the value of success, which can be either 1 (successful) or 0 (unsuccessful)
                int success = jsonObject.getInt(httpJsonParser.getKeySuccess());
                JSONObject device;
                // If the retrieval was successful
                if (success == 1) {
                    // Gets the details of the device
                    device = jsonObject.getJSONObject(httpJsonParser.getKeyData());
                    // For each device gets its name and adds it to the String details
                    //String deviceDescription = device.getString("Description");
                    details[0] = "OK";
                    details[1] = device.getString("Name");
                    details[2] = device.getString("Description");
                }
                else{
                    details[0] = "Problem";
                    details[1] = jsonObject.getString(httpJsonParser.getKeyMessage());
                }
            }
            catch (Exception e) {
                details[0] = "Problem";
                details[1] = "There was a connection problem. Please try again";
            }
            // Returns the list
            return details;
        }

        // This method takes the value returned by the previous one and then uses it, in this case to set
        // the text of resultTextView to be the list of names of all the devices
        public void onPostExecute(String[] result) {
//            if (result[0].equals("OK")){
//                ResultActivity.nameTextView.setText(result[1]);
//                ResultActivity.info_text.setText(result[2]);
//            }
//            else{
//                ResultActivity.nameTextView.setText(result[1]);
//            }
        }
    }
}
