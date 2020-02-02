package com.example.scannerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.scannerapp.R;
import androidx.recyclerview.widget.*;

import com.example.scannerapp.adapter.DeviceAdapter;
import com.example.scannerapp.adapter.Device;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.*;


public class HomeFragment extends Fragment {

    public static String result;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Device> devices;
    private DeviceAdapter dp;
    static HomeFragment INSTANCE;

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

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ScanCodeActivity.class));
            }
        });

        recyclerView = root.findViewById(R.id.device_list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        devices = new ArrayList<>();
        dp = new DeviceAdapter(getContext(), devices);
        recyclerView.setAdapter(dp);

        devices.add(new Device("Laptop", 5011));
        devices.add(new Device("Computer", 5022));
        devices.add(new Device("Server", 5031));
        devices.add(new Device("Workstation", 5052));
        devices.add(new Device("Printer", 5066));

        return root;
    }
}
