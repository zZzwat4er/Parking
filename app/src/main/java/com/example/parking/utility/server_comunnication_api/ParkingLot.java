package com.example.parking.utility.server_comunnication_api;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ParkingLot {
    public @SerializedName("id") @Nullable String id;
    public @SerializedName("type") @Nullable Integer type;

    private String typeToString(){
        if(type != null){
            switch (type){
                case 3: return "(на 2 машины) - 5.000₽";
                case 2: return "(увеличенное) - 4000₽";
                default:
                case 1: return "3.000₽";
            }
        }
        return "null";
    }

    @Override
    public String toString() {
        if(id != null && type != null) return String.format("%s - %s", id, typeToString());
        return "some lots are null";
    }
}
