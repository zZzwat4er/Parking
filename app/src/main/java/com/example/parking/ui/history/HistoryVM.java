package com.example.parking.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryVM extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoryVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is History");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
