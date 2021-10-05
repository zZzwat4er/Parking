package com.example.parking.ui.vehicle;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleAddViewModel extends ViewModel {

    private Integer errCode;
    private MutableLiveData<ServerReqCodes> sOut;

    public static Boolean isFromVehicleTab = false;

    public VehicleAddViewModel(){
        sOut = new MutableLiveData<>();
        sOut.setValue(ServerReqCodes.NONE);
    }

    public LiveData<ServerReqCodes> getOutPutCode(){ return sOut; }
    public void resetOutPutCode(){ sOut.setValue(ServerReqCodes.NONE);}
    public Integer getErrorCode(){
        if(sOut.getValue() == ServerReqCodes.ERR) return errCode;
        else return 0;
    }


    public void serverRequest(@NonNull final Activity activity, String plates, String mainCid){
        sOut.postValue(ServerReqCodes.NONE);
        if(StringChecker.isCard(mainCid) &&
                StringChecker.isPlates(plates)){
            try {
                comAPI.addCar(AccountHolder.email,
                        AccountHolder.passwordHush,
                        plates,
                        Integer.parseInt(mainCid),
                        activity.getApplicationContext(),
                        new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        AccountHolder.account = JSONPars.parseAccount(respond);
                        if(AccountHolder.account != null) {
                            AccountHolder.saveData(activity.getApplication());
                            sOut.postValue(ServerReqCodes.SUC);
                        }
                        else{
                            ServerError err = JSONPars.parseErrorServer(respond);
                            errCode = err.code;
                            sOut.postValue(ServerReqCodes.ERR);
                        }
                    }
                });
            }catch (Exception e){}
        }
    }
}
