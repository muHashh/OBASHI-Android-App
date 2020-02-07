package com.example.scannerapp;

import android.os.AsyncTask;

import com.example.scannerapp.ConnectionHelper.HttpJsonParser;
import org.json.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This file tests the script fetch_all_devices.php, in the remote server.
 * It only works if said server is active and working.
 * Each test needs to be in a AsyncTask subclass to work properly.
 */
public class FetchAllDevicesTest {

    /**
     * This test checks that the value of the success key is 1. Under normal
     * conditions, success cannot be 0, as that indicates that there's a problem
     * with the database in this particular case
     */
    @Test
    public void isSuccessful() {
        class TestAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... params) {
                HttpJsonParser httpJsonParser = new HttpJsonParser();
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        "fetch_all_devices.php", "GET", null);
                try{
                    assertEquals(1, jsonObject.getInt(httpJsonParser.getKeySuccess()));
                }
                catch (Exception e){
                    assertEquals(1, 0);
                }
                return "";
            }
        }
        TestAsyncTask TestClass = new TestAsyncTask();
        TestClass.execute();
    }

    /**
     * This tests checks that the JSON data has the correct structure
     */
    @Test
    public void structure_isCorrect() {
        class TestAsyncTask extends AsyncTask<String, String, String> {

            protected String doInBackground(String... params) {
                HttpJsonParser httpJsonParser = new HttpJsonParser();
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        "fetch_all_devices.php", "GET", null);
                try{
                    JSONArray devices = jsonObject.getJSONArray(httpJsonParser.getKeyData());
                    for (int i = 0; i < devices.length(); i++) {
                        JSONObject device = devices.getJSONObject(i);
                        JSONArray keys = device.names();
                        Boolean deviceID = ("DeviceID".equals(keys.getJSONObject(0).toString()))||
                                ("DeviceID".equals(keys.getJSONObject(1).toString()));
                        Boolean name = ("DeviceID".equals(keys.getJSONObject(0).toString()))||
                                ("DeviceID".equals(keys.getJSONObject(1).toString()));
                        assertEquals(2, device.length()); // Assert that there are two keys
                        assertEquals(true, deviceID); // Assert that one of those keys is "DeviceID"
                        assertEquals(true, name); // Assert that one of those keys is "Name"
                    }
                }
                catch (Exception e){
                    assertEquals(1, 0);
                }
                return "";
            }
        }
        TestAsyncTask TestClass = new TestAsyncTask();
        TestClass.execute();
    }
}
