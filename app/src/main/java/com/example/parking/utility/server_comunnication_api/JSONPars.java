package com.example.parking.utility.server_comunnication_api;

import com.example.parking.utility.Account;
import com.example.parking.utility.ServerError;
import com.google.gson.Gson;

public class JSONPars {

    public static Account parseAccount(String json){

        Gson gson = new Gson();
        Account acc = gson.fromJson(json, Account.class);
        if(acc.mEmail == null || acc.mFirstName == null || acc.mPhone == null) acc = null;
        return acc;
    }
    public static Boolean parseSuccess(){
        return true;
    }
    public static ServerError parseErrorServer(String json) {
        Gson gson = new Gson();
        ServerError err = gson.fromJson(json, ServerError.class);
        if(err.code == null || err.msg == null) err = null;
        return err;
    }
}
