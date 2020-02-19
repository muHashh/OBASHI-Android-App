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

public class AboutFragment extends Fragment{

    private AboutViewModel aboutViewModel;
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        root = inflater.inflate(R.layout.fragment_about, container, false);

        CardView card1 = root.findViewById(R.id.card_view1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CardOpenActivity.class);
                intent.putExtra("LAYOUT_NAME", R.layout.card_app);
                startActivity(intent);

            }
        });

        CardView card3 = root.findViewById(R.id.card_view3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CardOpenActivity.class);
                intent.putExtra("LAYOUT_NAME", R.layout.card_devs);
                startActivity(intent);
            }
        });

        CardView card4 = root.findViewById(R.id.card_view4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CardOpenActivity.class);
                intent.putExtra("LAYOUT_NAME", R.layout.card_tech);
                startActivity(intent);

            }
        });

        return root;

    }
}