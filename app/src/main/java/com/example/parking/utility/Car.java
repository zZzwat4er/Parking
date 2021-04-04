package com.example.parking.utility;



import androidx.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.google.gson.annotations.SerializedName;
import java.util.Date;


public class Car {

    public @SerializedName("id") Integer id;
    public @SerializedName("tariff") @Nullable Integer tariff;
    public @SerializedName("new_tariff") @Nullable Integer newTariff;
    public @SerializedName("parking_lot_type") @Nullable Integer parkingLotType;
//    public @SerializedName("new_parking_lot_type") @Nullable Integer newParkingLotType;
    public @SerializedName("parking_lot_id") @Nullable String parkingLotName;
//    public @SerializedName("new_parking_lot_id") @Nullable String newParkingLotName;
    public @SerializedName("plates") String plates;
    public @SerializedName("payed_till") @Nullable Date payedTill;
    public @SerializedName("is_auto_cont") Boolean isAutoCount;
    public @SerializedName("main_card") Integer mainCard;
    public @SerializedName("second_main_card") @Nullable Integer secondMainCard;
    public @SerializedName("additional_cards") @Nullable Integer[] additionalCards;

    public String getTariffName(){
        if (tariff == null) return "";
        switch (tariff){
            case 0: return "ежедневно";
            case 1: return "помесячно";
            case 2: return "собственик";
            default: return "";
        }
    }
    public String getDate(){
        if(payedTill == null) return "-\t";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, hh:mm");
        return dateFormat.format(payedTill);
    }
}
