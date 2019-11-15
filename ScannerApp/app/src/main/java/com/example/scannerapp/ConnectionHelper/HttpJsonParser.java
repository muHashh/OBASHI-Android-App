package com.example.scannerapp.ConnectionHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

public class HttpJsonParser {

    private static InputStream is = null;
    private static JSONObject jObj = null;
    private static String json = "";
    private HttpURLConnection urlConnection = null;

    /**
     * This method is used for making requests to the remote server
     * @param url A String which has the URL of the server, followed by the name of the script to execute
     * @param method A String which can be either GET, for HTTP GET requests, or POST, for HTTP POST requests
     * @param params A map of key-value pairs which contains the parameters necessary for the request
     * @return A JSON object which contains the response, following the format outlined in the Wiki
     */
    public JSONObject makeHttpRequest(String url, String method, Map<String, String> params){
        try {
            Uri.Builder builder = new Uri.Builder();
            URL urlObj;
            String encodedParams = "";
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()){
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            if (builder.build().getEncodedQuery() != null) {
                encodedParams =  builder.build().getEncodedQuery();
            }
            if ("GET".equals(method)) {
                url = url + "?" + encodedParams;
                urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                urlConnection.setRequestMethod(method);
            } else{
                urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(encodedParams.getBytes().length));
                urlConnection.getOutputStream().write(encodedParams.getBytes());
            }

            urlConnection.connect();
            is = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            jObj = new JSONObject(json);
        } catch (Exception e){
            Log.e("Exception", "Error parsing data " + e.toString());
        }
        return jObj;
    }
}
