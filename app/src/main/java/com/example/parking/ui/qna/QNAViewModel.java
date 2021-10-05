package com.example.parking.ui.qna;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QNAViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QNAViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QNA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
