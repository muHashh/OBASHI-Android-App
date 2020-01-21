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

    private static class FetchDeviceAsyncTask extends AsyncTask<String, String, String> {

        String details = "";

        @Override
        protected String doInBackground(String... params) {
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
                    // For each device gets its name and adds it to the String devicesLis
                    String deviceName = device.getString("Name");
                    String deviceDescription = device.getString("Description");
                    details = deviceName + " " + deviceDescription;
                }
                else{
                    details = jsonObject.getString(httpJsonParser.getKeyMessage());
                }
            }
            catch (Exception e) {
                details = "There was a connection problem. Please try again";
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
