package com.example.parking.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutVM extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is About page");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
