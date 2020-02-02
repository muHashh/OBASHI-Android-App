package com.example.scannerapp.ui.ar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;

import com.example.scannerapp.MainActivity;
import com.example.scannerapp.R;

public class ArAppFragment extends Fragment {

    private ArAppViewModel arAppViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arAppViewModel =
                ViewModelProviders.of(this).get(ArAppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ar, container, false);

        startActivityForResult(new Intent(getActivity(), ArActivity.class), 1);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}

