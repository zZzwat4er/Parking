package com.example.parking.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapVM extends ViewModel {

    private MutableLiveData<String> mText;

    public MapVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is map");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
