package com.example.parking.ui.Vehicle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parking.LoginActivity;
import com.example.parking.ui.Vehicle.RecyclerView.Adapter;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleViewModel extends ViewModel {

    public enum OutputCodes{
        NONE, SUC, ERR
    }

    public static int carID;
    private int errorCode;
    private MutableLiveData<OutputCodes> outputCode;

    public VehicleViewModel() {
        outputCode = new MutableLiveData<>();
        outputCode.setValue(OutputCodes.NONE);
    }

    public Integer getErrorCode(){
        if(outputCode.getValue() == OutputCodes.ERR) return errorCode;
        else return 0;
    }

    public LiveData<OutputCodes> getOutPutCode(){ return outputCode; }
    public void resetOutPutCode(){outputCode.setValue(OutputCodes.NONE);}

    public void serverRequest(@NonNull final Activity activity){
        outputCode.postValue(OutputCodes.NONE);
        comAPI.login(AccountHolder.email,
                AccountHolder.passwordHush,
                activity.getApplicationContext(),
                new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        AccountHolder.account = JSONPars.parseAccount(respond);
                        if(AccountHolder.account != null) {
                            AccountHolder.saveData(activity.getApplication());
                            outputCode.postValue(OutputCodes.SUC);
                        }else{
                            ServerError err = JSONPars.parseErrorServer(respond);
                            if(err != null){
                                errorCode = err.code;
                            }
                            outputCode.postValue(OutputCodes.ERR);
                        }
                    }
                });
    }

}
