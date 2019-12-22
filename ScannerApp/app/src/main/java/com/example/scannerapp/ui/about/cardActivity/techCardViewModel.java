package com.example.scannerapp.ui.about.cardActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class techCardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public techCardViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
