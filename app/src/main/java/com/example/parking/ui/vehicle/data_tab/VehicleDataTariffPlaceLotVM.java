package com.example.parking.ui.vehicle.data_tab;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parking.utility.Account;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.ParkingLot;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleDataTariffPlaceLotVM extends ViewModel {
    private Integer errCode;
    private MutableLiveData<ServerReqCodes> sOut;
    private ParkingLot[] lots;

    public VehicleDataTariffPlaceLotVM(){
        sOut = new MutableLiveData<>(ServerReqCodes.NONE);
    }

    public LiveData<ServerReqCodes> getOutPutCode(){ return sOut; }
    public ParkingLot[] getLots(){return lots;}
    public Integer getErrorCode(){
        if(sOut.getValue() == ServerReqCodes.ERR) return errCode;
        return 0;
    }

    public void serverRequest(@NonNull final Activity activity){
        comAPI.getAvailableParkingLots(
                activity.getApplicationContext(),
                new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        if((lots = JSONPars.parseParkingLots(respond)) != null) {
                            sOut.postValue(ServerReqCodes.SUC);
                            errCode = 0;
                        }
                        else{
                            sOut.postValue(ServerReqCodes.ERR);
                            try {
                                ServerError err = JSONPars.parseErrorServer(respond);
                                errCode = err.code;
                            }catch(NullPointerException e){
                                Log.d("Failed error parse", respond);
                            }
                        }
                    }
                });
    }
}
