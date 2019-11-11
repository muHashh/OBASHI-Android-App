package com.example.scannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import com.example.scannerapp.ui.home.HomeFragment;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final String TAG = "ScanCodeActivity";
    private static final int REQUEST_CODE = 1;
    ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

        verifyPermissions();
    }

    @Override
    public void handleResult(Result result){

        FetchDeviceAsyncTask connectMySql = new FetchDeviceAsyncTask();
        connectMySql.execute(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume(){
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

    private void verifyPermissions() {
        Log.d(TAG, "Verifying permissions: asking user for camera permission");
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            onResume();
        }
        else {
            ActivityCompat.requestPermissions(ScanCodeActivity.this,
                    permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

    private class FetchDeviceAsyncTask extends AsyncTask<String, String, String> {

        String BASE_URL = "http://10.0.2.2/ObashiDB/"; // The address where the server is. 10.0.2.2 is basically localhost,
        // so for deployment it should be changed for an actual address
        String KEY_SUCCESS = "success";
        String KEY_DATA = "data"; // In the JSON object, data is a key whose value is an array of data from devices
        String details = "";

        @Override
        protected String doInBackground(String... params) {
            // Creates a new HttpJsonParser object
            // Then executes the method makeHttpRequest, with arguments the URL,
            // which is the server URL plus the name of the script, "GET", because it is
            // getting data from the database, not changing it, and null, because this request
            // doesn't need any parameter. If this wasn't the case, the parameters should be
            // in a Map<String, String>. Finally, a JSON object containing the response is returned.
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("DeviceID", params[0]);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_device_details.php", "GET", parameters);
            try {
                // Gets the value of success, which can be either 1 (successful) or 0 (unsuccessful)
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject device;
                // If the retrieval was successful
                if (success == 1) {
                    // Gets the details of the device
                    device = jsonObject.getJSONObject(KEY_DATA);
                    // For each device gets its name and adds it to the String devicesLis
                    String deviceName = device.getString("Name");
                    String deviceDescription = device.getString("Description");
                    details = deviceName + " " + deviceDescription;
                    details += "/n";

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            // Returns the list
            return details;
        }

        // This method takes the value returned by the previous one and then uses it, in this case to set
        // the text of resultTextView to be the list of names of all the devices
        protected void onPostExecute(String result) {
            HomeFragment.resultTextView.setText(result);
        }

    }
}
