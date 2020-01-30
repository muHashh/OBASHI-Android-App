package com.example.scannerapp.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    private String name;
    private int id;

    public Device(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Device(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    String getName() {
        return name;
    }
}
