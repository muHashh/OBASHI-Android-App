package com.example.scannerapp.ui.home;

import android.os.AsyncTask;
import android.view.View;
import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import org.json.JSONObject;
import java.util.HashMap;

public class FetchDeviceAsyncTask extends AsyncTask<String, String, String[]> {

    @Override
    protected String[] doInBackground(String... params) {
        String[] data = new String[4];
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
                // For each device gets its DeviceID, name and description, and adds them to the array data
                data[0] = "OK";
                data[1] = String.valueOf(device.getInt("DeviceID"));
                data[2] = device.getString("Name");
                data[3] = device.getString("Description");
            }
            // If the connection was successful, but there was some other problem,
            // like the device is not in the database
            else{
                data[0] = "Problem";
                data[1] = jsonObject.getString(httpJsonParser.getKeyMessage());
            }
        }
        // If there is a connection problem, or an internal problem in the app
        catch (Exception e) {
            data[0] = "Problem";
            data[1] = "There was a connection problem. Please try again";
        }
        // Returns the array
        return data;
    }

    // This method takes the array returned by the previous one and then uses it, in this case to set
    // different fields on the home fragment to hold the name and description of the device
    @Override
    public void onPostExecute(String[] result) {
        HomeFragment hf = HomeFragment.getInstance();
        if(result[0].equals("OK")) {
            hf.name.setText(result[2]);
            hf.desc.setText(result[3]);
            hf.ll.setVisibility(View.VISIBLE);
            hf.error.setVisibility(View.GONE);
        } else {
            hf.errorName.setText(result[1]);
            hf.error.setVisibility(View.VISIBLE);
            hf.ll.setVisibility(View.GONE);
        }
    }
}
