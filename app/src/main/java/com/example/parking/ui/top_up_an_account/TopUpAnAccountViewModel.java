package com.example.parking.ui.top_up_an_account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class TopUpAnAccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TopUpAnAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Top up an account");
    }

    public void update(Integer paymentValue){
        mText.setValue("Баланс после пополнения: " + paymentValue);
    }

    public LiveData<String> getText() {
        return mText;
    }

}
