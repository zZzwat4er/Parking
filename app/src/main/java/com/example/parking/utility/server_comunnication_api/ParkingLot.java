package com.example.parking.utility.server_comunnication_api;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ParkingLot {
    public @SerializedName("id") @Nullable String id;
    public @SerializedName("type") @Nullable Integer type;
}
