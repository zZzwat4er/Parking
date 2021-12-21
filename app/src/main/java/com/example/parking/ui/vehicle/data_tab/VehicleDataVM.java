package com.example.parking.ui.vehicle.data_tab;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parking.utility.Account;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleDataVM extends ViewModel {

    private Integer errCode;
    private MutableLiveData<ServerReqCodes> sOut;

    public VehicleDataVM(){
        sOut = new MutableLiveData<>(ServerReqCodes.NONE);
    }

    public LiveData<ServerReqCodes> getOutPutCode(){ return sOut; }
    public Integer getErrorCode(){
        if(sOut.getValue() == ServerReqCodes.ERR) return errCode;
        return 0;
    }

    public void serverRequest(@NonNull final Activity activity, Integer catID, String plates){
        comAPI.updatePlates(
                AccountHolder.email,
                AccountHolder.passwordHush,
                catID,
                plates,
                activity.getApplicationContext(),
                new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        Account acc;
                        if((acc = JSONPars.parseAccount(respond)) != null) {
                            AccountHolder.account = acc;
                            AccountHolder.saveData(activity.getApplication());
                            sOut.postValue(ServerReqCodes.SUC);
                            errCode = 0;
                        }
                        else{
                            ServerError err = JSONPars.parseErrorServer(respond);
                            sOut.postValue(ServerReqCodes.ERR);
                            errCode = err.code;
                        }
                    }
                });
    }

}
