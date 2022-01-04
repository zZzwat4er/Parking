package com.example.parking.ui.login.sign_up;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class SignUpVM extends ViewModel{
    private Integer errCode;
    private MutableLiveData<ServerReqCodes> sOut;

    public SignUpVM(){
        sOut = new MutableLiveData<>(ServerReqCodes.NONE);
    }

    public LiveData<ServerReqCodes> getOutPutCode(){ return sOut; }
    public Integer getErrorCode(){
        if(sOut.getValue() == ServerReqCodes.ERR) return errCode;
        return 0;
    }

    public void serverRequest(@NonNull final Activity activity, final String email, final String passhash,
                              String phone, String firstName, String lastName, String secondName){
        comAPI.register(email,
                passhash,
                phone,
                firstName,
                lastName,
                (secondName.isEmpty()) ? null : secondName,
                activity.getApplicationContext(),
                new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        Account acc;
                        if((acc = JSONPars.parseAccount(respond)) != null) {
                            AccountHolder.passwordHush = passhash;
                            AccountHolder.email = email;
                            AccountHolder.account = acc;
                            AccountHolder.saveData(activity.getApplication());
                            sOut.postValue(ServerReqCodes.SUC);
                            errCode = 0;
                        }
                        else{
                            Log.d("respond", respond);
                            ServerError err = JSONPars.parseErrorServer(respond);
                            sOut.postValue(ServerReqCodes.ERR);
                            if(err != null) errCode = err.code;
                            else errCode = -1;
                        }
                    }
                });
    }
}
