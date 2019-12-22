package com.example.scannerapp.ui.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.scannerapp.R;
import com.example.scannerapp.ui.about.cardActivity.appCard;
import com.example.scannerapp.ui.about.cardActivity.devsCard;
import com.example.scannerapp.ui.about.cardActivity.techCard;

public class AboutFragment extends Fragment{

    private AboutViewModel aboutViewModel;
    private CardView app,devs,tech;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        devs = root.findViewById(R.id.card_view3);
        app = root.findViewById(R.id.card_view1);
        tech = root.findViewById(R.id.card_view4);

        devs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), devsCard.class));
            }
        });
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), appCard.class));
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), techCard.class));
            }
        });

        return root;

        }
    }