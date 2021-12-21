package com.example.parking.ui.faced_a_problem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FasedAProblemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FasedAProblemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QNA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
