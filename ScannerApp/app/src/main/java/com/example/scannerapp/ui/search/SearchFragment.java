package com.example.scannerapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.R;

import java.util.HashMap;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private static SearchFragment INSTANCE;
    HashMap<Integer, String> devices;

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
        FetchAllDevicesAsyncTask getDevices = new FetchAllDevicesAsyncTask();
        getDevices.execute();

        return root;
    }
}
