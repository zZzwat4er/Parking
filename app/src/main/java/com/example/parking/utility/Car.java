package com.example.parking.utility;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Car {

    public @SerializedName("id") Integer id;
    public @SerializedName("tariff") @Nullable Integer tariff;
    public @SerializedName("parking_lot_type") @Nullable Integer parkingLotType;
    public @SerializedName("parking_lot_id") @Nullable Integer parkingLotId;
    public @SerializedName("plates") String plates;
    public @SerializedName("payed_till") @Nullable Date payedTill;
    public @SerializedName("is_auto_cont") Boolean isAutoCount;
    public @SerializedName("main_card") Integer mainCard;
    public @SerializedName("second_main_card") @Nullable Integer secondMainCard;
    public @SerializedName("additional_cards") @Nullable Integer[] additionalCards;
}
