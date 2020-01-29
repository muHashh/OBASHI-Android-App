package com.example.scannerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.CardOpen;
import com.example.scannerapp.R;
import com.example.scannerapp.ResultActivity;
import com.example.scannerapp.ScanCodeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    public  static TextView resultTextView;
    public static String result;
    LinearLayout linearLayout;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        resultTextView = root.findViewById(R.id.result_text);

        linearLayout = root.findViewById(R.id.linear_layout_card);

        for (int i = 1; i <= 5; i++) {
            TextView textView = new TextView(getContext());
            CardView cardView = new CardView(getContext());
            textView.setText("TextView " + String.valueOf(i));
//            cardView.set
            linearLayout.addView(textView);
        }



        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ScanCodeActivity.class));
            }
        });

        CardView card5 = root.findViewById(R.id.card_view_home2);
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                startActivity(intent);

            }
        });

        return root;

    }
}