package com.example.scannerapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.scannerapp.R;
import java.util.*;

import com.example.scannerapp.ResultActivity;
import com.example.scannerapp.adapter.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder>{

    private LayoutInflater layoutInflater;
    private ArrayList<Device> devices;

    public DeviceAdapter(Context context, ArrayList<Device> devices) {
        this.layoutInflater = LayoutInflater.from(context);
        this.devices = devices;
    }

    public void swap(ArrayList<Device> devices){
        this.devices = devices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.item_card, parent, false);
        return new DeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
        holder.txtName.setText(devices.get(position).getName());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ResultActivity.class);
                intent.putExtra("NAME", holder.txtName.getText());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder{

        private TextView txtName;
        private CardView card;

        DeviceHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt);
            card = itemView.findViewById(R.id.device_card);
        }
    }
}


