package com.example.parking.utility.server_comunnication_api;

import android.util.Log;

import com.example.parking.utility.Account;
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
    public static Integer parseErrorServer(){
        return 1;
    }
}
