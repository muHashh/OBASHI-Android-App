package com.example.scannerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.scannerapp.R;
import androidx.recyclerview.widget.*;

import com.example.scannerapp.ui.ar.ArActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.*;

/**
 *
 * When you first enter the homepage it will produce the layout from fragment_home.xml. If however
 * you have scanned machines it will dynamically display a list of cards with details of all the
 * scanned machines. If you click on ones of these cards it will activate ResultActivity class to
 * display the details of that machine in
 */


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    static HomeFragment INSTANCE;
    LinearLayout ll;
    LinearLayout error;
    TextView name;
    TextView errorName;
    TextView desc;

    public static HomeFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        INSTANCE = this;

        ll = root.findViewById(R.id.results);
        ll.setVisibility(View.GONE);

        error = root.findViewById(R.id.error);
        error.setVisibility(View.GONE);
        errorName = root.findViewById(R.id.errorMessage);

        name = root.findViewById(R.id.machine_name_view);
        desc = root.findViewById(R.id.description);

        CardView prevNodes = root.findViewById(R.id.card_prevNode1);
        prevNodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArActivity.class);
                intent.putExtra("NODE", "prev");
                startActivity(intent);

            }
        });

        CardView nextNodes = root.findViewById(R.id.card_nextNode1);
        nextNodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArActivity.class);
                intent.putExtra("NODE", "next");
                startActivity(intent);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ScanCodeActivity.class));
            }
        });

        return root;
    }
}
