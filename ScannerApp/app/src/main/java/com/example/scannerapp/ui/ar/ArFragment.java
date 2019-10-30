package com.example.scannerapp.ui.ar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.R;

public class ArFragment extends Fragment {

    private ArViewModel arViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arViewModel =
                ViewModelProviders.of(this).get(ArViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ar, container, false);

        return root;
    }
}