package com.example.parking.ui.user_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserInfoVM extends ViewModel {

    private MutableLiveData<String> mText;

    public UserInfoVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is User info");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
