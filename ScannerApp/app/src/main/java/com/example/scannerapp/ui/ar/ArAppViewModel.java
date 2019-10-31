package com.example.scannerapp.ui.ar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArAppViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ArAppViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}