package com.example.scannerapp.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.scannerapp.R;
import com.example.scannerapp.ui.search.FetchAllDevicesAsyncTask;

public class SearchResultsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        Intent intent = getIntent();
        Log.e("error123456", "hello there");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e("error123456", query);
            FetchAllDevicesAsyncTask search = new FetchAllDevicesAsyncTask(query);
            search.execute();
        }
        else {
            Log.e("error123456", "not found");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e("error123456", query);
            FetchAllDevicesAsyncTask search = new FetchAllDevicesAsyncTask(query);
            search.execute();
        }
        else {
            Log.e("error123456", "not found");
        }
    }

}
