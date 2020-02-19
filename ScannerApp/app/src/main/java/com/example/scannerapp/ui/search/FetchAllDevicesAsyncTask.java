package com.example.scannerapp.ui.search;

import android.os.AsyncTask;
import android.view.View;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Class used for retrieving all devices that can be found on the database.
 * To use it, call the method execute(), without parameters, which does not return anything.
 * As of now, it can only be use by SearchFragment, as it has to update a field in that class
 */
public class FetchAllDevicesAsyncTask extends AsyncTask<Void, String, HashMap<Integer, String>>{
    private HashMap<Integer, String> data = new HashMap<>(); //HashMap that will contain the data to be used
    // Constant that will be used to add an error message to data
    // As no device can have key -1, we can check if there is some problem by looking if
    // ERROR_KEY is a key of data
    private final int ERROR_KEY = -1;
    private String query;

    public FetchAllDevicesAsyncTask(String newQuery){
        query = newQuery;
    }

    @Override
    protected HashMap<Integer, String> doInBackground(Void... params) {
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
                // For each device gets its name and adds it to the HashMap
                for (int i = 0; i < devices.length(); i++) {
                    JSONObject device = devices.getJSONObject(i);
                    String deviceName = device.getString("Name");
                    int id = device.getInt("DeviceID");
                    data.put(id, deviceName);
                }

            }
            // If the connection was successful, but the retrieval was not,
            // empty the HashMap and add a the corresponding error message
            else{
                data.clear();
                data.put(ERROR_KEY, jsonObject.getString(httpJsonParser.getKeyMessage()));
            }
        }
        // If there is a problem handling the connection or the JSON object,
        // empty the HashMap and add a generic error message
        catch (Exception e) {
            data.clear();
            data.put(-1, "There was a connection problem. Please try again");
        }
        // Returns the list
        return data;
    }

    // This method takes the value returned by the previous one and then uses it, in this case to set
    // HashMap devices in SearchFragment to be the list
    protected void onPostExecute(HashMap<Integer, String> result) {
        SearchFragment sf = SearchFragment.getInstance();
        sf.devices = result;
        Similarity similar = new Similarity();
        LinkedHashMap<Integer, Double> orderedDevices = similar.getSimilar(result, query);
        sf.orderedDevices = orderedDevices;
        int i = 0;
        while(!orderedDevices.isEmpty() && i < 3){
            i++;
            int key = orderedDevices.entrySet().iterator().next().getKey();
            orderedDevices.remove(key);
            if(i == 0){
                sf.button1.setText(result.get(key));
                sf.button1.setVisibility(View.VISIBLE);
            }else if(i == 1){
                sf.button2.setText(result.get(key));
                sf.button2.setVisibility(View.VISIBLE);
            }else{
                sf.button3.setText(result.get(key));
                sf.button3.setVisibility(View.VISIBLE);
            }
        }
    }

}