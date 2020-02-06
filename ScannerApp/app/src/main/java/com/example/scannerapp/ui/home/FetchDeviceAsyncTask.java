package com.example.scannerapp.ui.home;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import org.json.JSONObject;
import java.util.HashMap;

public class FetchDeviceAsyncTask extends AsyncTask<String, String, String[]> {

    String[] data = new String[4];
    private String name;
    private TextView tv;
    private TextView Desc;

    public FetchDeviceAsyncTask(TextView tv, TextView Desc) {
        this.tv = tv;
        this.Desc = Desc;
    }

    public String getName() {
        if (name == null)
            return "Device name not found";
        return name;
    }

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
                data[3] = device.getString("Description");
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
    @Override
    public void onPostExecute(String[] result) {
        if(result[0].equals("OK")) {
            tv.setText(result[2]);
            Desc.setText(result[3]);
        } else {
            tv.setText(result[1]);
        }
    }
}
