package com.example.scannerapp.ui.about.cardActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.scannerapp.R;


public class devsCard extends Fragment {

    private devsCardViewModel devsCardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        devsCardViewModel =
                ViewModelProviders.of(this).get(devsCardViewModel.class);
        View root = inflater.inflate(R.layout.card_devs, container, false);

        return root;
    }

}
