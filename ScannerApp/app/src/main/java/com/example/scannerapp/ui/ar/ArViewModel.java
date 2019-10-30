package com.example.scannerapp.ui.ar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ArViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}