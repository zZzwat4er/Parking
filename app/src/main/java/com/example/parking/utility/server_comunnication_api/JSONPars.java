package com.example.parking.utility.server_comunnication_api;

import android.util.Log;

import com.example.parking.utility.Account;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.ServerSuccess;
import com.google.gson.Gson;

import java.util.ArrayList;

public class JSONPars {

    public static Account parseAccount(String json){

        Gson gson = new Gson();
        Account acc = gson.fromJson(json, Account.class);
        if(acc != null)
            if(acc.mEmail == null || acc.mFirstName == null || acc.mPhone == null)
                acc = null;
        return acc;
    }

    public static Boolean parseSuccess(String json){
        Gson gson = new Gson();
        ServerSuccess suc = gson.fromJson(json, ServerSuccess.class);
        return suc != null;
    }

    public static ParkingLot[] parseParkingLots(String json){
        Gson gson = new Gson();
        ParkingLot[] lots = gson.fromJson(json, ParkingLot[].class);
        if(lots != null) {
            Log.d("parse lots", String.format("%d", lots.length));
            ArrayList<ParkingLot> temp = new ArrayList<>();
            for (ParkingLot lot : lots) {
                if (lot != null && lot.id != null && lot.type != null) temp.add(lot);
            }
            lots = new ParkingLot[temp.size()];
            lots = temp.toArray(lots);
        }
        return lots;
    }

    public static ServerError parseErrorServer(String json) {
        Gson gson = new Gson();
        ServerError err = gson.fromJson(json, ServerError.class);
        if(err != null && (err.code == null || err.msg == null)) err = null;
        return err;
    }
}
