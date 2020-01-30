package com.example.scannerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scannerapp.R;
import androidx.recyclerview.widget.*;

import com.example.scannerapp.ScanCodeActivity;
import com.example.scannerapp.adapter.DeviceAdapter;
import com.example.scannerapp.adapter.Device;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;


public class HomeFragment extends Fragment {

    public TextView resultTextView;
    public static String result;
    LinearLayout linearLayout;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Device> devices;
    private DeviceAdapter dp;

    public void addDevice(Device device) {
        devices.add(device);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ScanCodeActivity.class));
            }
        });


        recyclerView = root.findViewById(R.id.device_list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        devices = new ArrayList<>();
        dp = new DeviceAdapter(getContext(), devices);
        recyclerView.setAdapter(dp);


        devices.add(new Device("Device Name", 501));
        devices.add(new Device("Device Name", 502));
        devices.add(new Device("Device Name", 501));
        devices.add(new Device("Device Name", 502));


        Intent intent = new Intent(getContext(), ScanCodeActivity.class);
        intent.putExtra("devices", devices);

        return root;
    }
}
