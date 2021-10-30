package com.example.parking.ui.top_up_an_account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parking.R;


public class TopUpAnAccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TopUpAnAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Баланс после пополнения: ");
    }

    public void update(Integer paymentValue){
        mText.setValue("Баланс после пополнения: " + paymentValue);
    }

    public LiveData<String> getText() {
        return mText;
    }

}
