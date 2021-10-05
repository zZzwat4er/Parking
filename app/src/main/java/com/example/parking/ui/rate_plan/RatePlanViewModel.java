package com.example.parking.ui.rate_plan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RatePlanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RatePlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Rate Plan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
