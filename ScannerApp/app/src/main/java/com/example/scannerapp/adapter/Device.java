package com.example.scannerapp.adapter;

public class Device {
    private String name;
    private int id;

    public Device(String name, int id) {
        this.name = name;
        this.id = id;
    }

    String getName() {
        return name;
    }
}
