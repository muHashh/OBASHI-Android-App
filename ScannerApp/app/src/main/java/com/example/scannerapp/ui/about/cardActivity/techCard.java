package com.example.scannerapp.ui.about.cardActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.R;

public class techCard extends Fragment {

    private techCardViewModel techCardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        techCardViewModel =
                ViewModelProviders.of(this).get(techCardViewModel.class);
        View root = inflater.inflate(R.layout.card_tech, container, false);

        return root;
    }

}
