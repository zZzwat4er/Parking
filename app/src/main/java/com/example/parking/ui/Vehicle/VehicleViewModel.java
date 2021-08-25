package com.example.parking.ui.Vehicle;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleViewModel extends ViewModel {

    public static int carID;
    private int errorCode;
    private MutableLiveData<ServerReqCodes> outputCode;

    public VehicleViewModel() {
        outputCode = new MutableLiveData<>();
        outputCode.setValue(ServerReqCodes.NONE);
    }

    public LiveData<ServerReqCodes> getOutPutCode(){ return outputCode; }
    public void resetOutPutCode(){outputCode.setValue(ServerReqCodes.NONE);}
    public Integer getErrorCode(){
        if(outputCode.getValue() == ServerReqCodes.ERR) return errorCode;
        else return 0;
    }

    public void serverRequest(@NonNull final Activity activity){
        outputCode.postValue(ServerReqCodes.NONE);
        comAPI.login(AccountHolder.email,
                AccountHolder.passwordHush,
                activity.getApplicationContext(),
                new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        AccountHolder.account = JSONPars.parseAccount(respond);
                        if(AccountHolder.account != null) {
                            AccountHolder.saveData(activity.getApplication());
                            outputCode.postValue(ServerReqCodes.SUC);
                        }else{
                            ServerError err = JSONPars.parseErrorServer(respond);
                            if(err != null){
                                errorCode = err.code;
                            }
                            outputCode.postValue(ServerReqCodes.ERR);
                        }
                    }
                });
    }

}
