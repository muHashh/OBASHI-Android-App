package com.example.scannerapp.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.R;
import com.example.scannerapp.ui.home.FetchDeviceAsyncTask;

import java.util.HashMap;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private static SearchFragment INSTANCE;
    HashMap<Integer, String> devices;
    HashMap<Integer, Double> orderedDevices;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button searchButton;
    int button1key = 0;
    int button2key = 0;
    int button3key = 0;
    int button4key = 0;
    int button5key = 0;
    String query;

    public static SearchFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        INSTANCE = this;

        button1 = root.findViewById(R.id.result1);
        button1.setVisibility(View.GONE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchDeviceAsyncTask fetchDevice = new FetchDeviceAsyncTask();
                fetchDevice.execute(String.valueOf(button1key));
            }
        });
        button2 = root.findViewById(R.id.result2);
        button2.setVisibility(View.GONE);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchDeviceAsyncTask fetchDevice = new FetchDeviceAsyncTask();
                fetchDevice.execute(String.valueOf(button2key));
            }
        });
        button3 = root.findViewById(R.id.result3);
        button3.setVisibility(View.GONE);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchDeviceAsyncTask fetchDevice = new FetchDeviceAsyncTask();
                fetchDevice.execute(String.valueOf(button3key));
            }
        });
        button4 = root.findViewById(R.id.result4);
        button4.setVisibility(View.GONE);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchDeviceAsyncTask fetchDevice = new FetchDeviceAsyncTask();
                fetchDevice.execute(String.valueOf(button4key));
            }
        });
        button5 = root.findViewById(R.id.result5);
        button5.setVisibility(View.GONE);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchDeviceAsyncTask fetchDevice = new FetchDeviceAsyncTask();
                fetchDevice.execute(String.valueOf(button5key));
            }
        });
        EditText searchText = root.findViewById(R.id.search_text);
        searchButton = root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("error123", searchText.toString());
                FetchAllDevicesAsyncTask fetchDevices = new FetchAllDevicesAsyncTask(searchText.getText().toString());
                fetchDevices.execute();
            }
        });
        return root;
    }
}
