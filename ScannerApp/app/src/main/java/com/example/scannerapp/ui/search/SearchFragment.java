package com.example.scannerapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.R;

import java.util.HashMap;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private static SearchFragment INSTANCE;
    HashMap<Integer, String> devices;
    HashMap<Integer, Double> orderedDevices;
    Button button1;
    Button button2;
    Button button3;

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
        button2 = root.findViewById(R.id.result2);
        button2.setVisibility(View.GONE);
        button3 = root.findViewById(R.id.result3);
        button3.setVisibility(View.GONE);

        return root;
    }
}
