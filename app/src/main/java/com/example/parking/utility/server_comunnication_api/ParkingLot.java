package com.example.parking.utility.server_comunnication_api;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ParkingLot {
    public final static int DOUBLE_PLACE = 3;
    public final static int BIG_PLACE = 2;
    public final static int SINGLE_PLACE = 1;

    public @SerializedName("id") @Nullable String id;
    public @SerializedName("type") @Nullable Integer type;

    private String typeToString(){
        if(type != null){
            switch (type){
                case DOUBLE_PLACE: return "(на 2 машины) - 5.000₽";
                case BIG_PLACE: return "(увеличенное) - 4000₽";
                default:
                case SINGLE_PLACE: return "3.000₽";
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
