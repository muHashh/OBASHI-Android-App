package com.example.scannerapp.ui.search;

import android.os.AsyncTask;
import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;

public class FetchAllDevicesAsyncTask extends AsyncTask<String, String, HashMap<Integer, String>>{
    HashMap<Integer, String> data = new HashMap<>();
    int errorKey = -1;

    @Override
    protected HashMap<Integer, String> doInBackground(String... params) {
        // Creates a new HttpJsonParser object
        // Then executes the method makeHttpRequest, with arguments the name of the script,
        // "GET", because it is getting data from the database, not changing it, and null,
        // because this request doesn't need any parameter. If this wasn't the case, the
        // parameters should be in a Map<String, String>. Finally, a JSON object containing
        // the response is returned.
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        JSONObject jsonObject = httpJsonParser.makeHttpRequest("fetch_all_devices.php", "GET", null);
        try {
            // Gets the value of success, which can be either 1 (successful) or 0 (unsuccessful)
            int success = jsonObject.getInt(httpJsonParser.getKeySuccess());
            JSONArray devices;
            // If the retrieval was successful
            if (success == 1) {
                // Gets the array of devices
                devices = jsonObject.getJSONArray(httpJsonParser.getKeyData());
                // For each device gets its name and adds it to the String devicesList
                for (int i = 0; i < devices.length(); i++) {
                    JSONObject device = devices.getJSONObject(i);
                    String deviceName = device.getString("Name");
                    int id = device.getInt("DeviceID");
                    data.put(id, deviceName);
                }

            }
            else{
                data.clear();
                data.put(errorKey, jsonObject.getString(httpJsonParser.getKeyMessage()));
            }
        }
        // If there is a problem handling the connection or the JSON object
        // return a generic connection problem message
        catch (Exception e) {
            data.clear();
            data.put(-1, "There was a connection problem. Please try again");
        }
        // Returns the list or error message
        return data;
    }

    // This method takes the value returned by the previous one and then uses it, in this case to set
    // the text of resultTextView to be the list of names of all the devices
    protected void onPostExecute(HashMap<Integer, String> result) {
        SearchFragment sf = SearchFragment.getInstance();

    }

}