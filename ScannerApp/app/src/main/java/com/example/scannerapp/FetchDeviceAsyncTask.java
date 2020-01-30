package com.example.scannerapp;

import android.os.AsyncTask;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;

import org.json.JSONObject;

import java.util.HashMap;

public class FetchDeviceAsyncTask extends AsyncTask<String, String, String[]> {

    String[] data = new String[3];

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
                // For each device gets its name and adds it to the String devicesList
                data[0] = "OK";
                data[1] = String.valueOf(device.getInt("DeviceID"));
                data[2] = device.getString("Name");
            }
            else{
                data[0] = "Problem";
                data[1] = jsonObject.getString(httpJsonParser.getKeyMessage());
            }
        }
        catch (Exception e) {
            data[0] = "Problem";
            data[1] = "There was a connection problem. Please try again";
        }
        // Returns the list
        return data;
    }

    // This method takes the value returned by the previous one and then uses it, in this case to set
    // the text of resultTextView to be the list of names of all the devices
    public void onPostExecute(String[] result) {
        if (result[0].equals("OK")){
            //HomeFragment.resultTextView.setText(result[2]);
            //HomeFragment.result = result[1];
        }
        else{
            //HomeFragment.resultTextView.setText(result[1]);
        }
    }
}
