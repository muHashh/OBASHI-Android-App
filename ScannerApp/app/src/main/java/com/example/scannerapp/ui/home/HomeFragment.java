package com.example.scannerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.scannerapp.R;
import androidx.recyclerview.widget.*;

import com.example.scannerapp.adapter.DeviceAdapter;
import com.example.scannerapp.adapter.Device;
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

    public static String result;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Device> devices;
    private DeviceAdapter dp;
    static HomeFragment INSTANCE;
    LinearLayout ll;

    public static HomeFragment getInstance() {
        return INSTANCE;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void notifyChange() {
        dp.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        INSTANCE = this;
        ll = root.findViewById(R.id.results);


        int begoneThot = View.GONE;
        ll.setVisibility(begoneThot);

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
