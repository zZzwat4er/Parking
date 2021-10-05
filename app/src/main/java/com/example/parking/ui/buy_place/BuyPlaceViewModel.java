package com.example.parking.ui.buy_place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BuyPlaceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BuyPlaceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Если хотите купить парковочное место, позвоните в отдел продаж:<Номер телефона>");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
