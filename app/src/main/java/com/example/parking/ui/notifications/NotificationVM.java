package com.example.parking.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationVM extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Notification");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
