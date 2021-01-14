package com.example.parking.utility;

import androidx.annotation.Nullable;

import java.util.Date;

public class Car {

    public Integer id;
    public @Nullable Integer tariff;
    public @Nullable Integer parkingLotType;
    public @Nullable Integer parkingLotId;
    public String plates;
    public @Nullable Date payedTill;
    public Boolean isAutoCount;
    public Integer mainCard;
    public @Nullable Integer secondMainCard;
    public @Nullable Integer[] additionalCards;
}
