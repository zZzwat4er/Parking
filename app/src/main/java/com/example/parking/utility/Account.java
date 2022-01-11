package com.example.parking.utility;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class Account {
    public @SerializedName("email") String mEmail;
    public @SerializedName("phone")String mPhone;
    public @SerializedName("name")String mFirstName;
    public @SerializedName("patronymic")@Nullable String mSecondName;
    public @SerializedName("surname")String mLastName;
    public @SerializedName("balance")Integer mBalance;
    public @SerializedName("cars")@Nullable Car[] mCars;

    public Account(String email, String firstName, @Nullable String secondName,
                    String lastName, String phone, Integer balance, @Nullable Car[] cars){
        mEmail = email;
        mPhone = phone;
        mFirstName = firstName;
        mSecondName = secondName;
        mLastName = lastName;
        mBalance = balance;
        mCars = cars;
    }

    //region Get/Set/Edit func
    public String getFullName(){
        return mLastName + " " + mFirstName + ((mSecondName != null)? " " + mSecondName : "");
    }

    @Nullable
    public Car getCarById(int id){
        for(Car car : mCars){
            if(car.id == id) return car;
        }
        return null;
    }

    @Nullable
    public Car[] getCars() {
        if (mCars != null) Arrays.sort(mCars, (o1, o2) -> o2.getId().compareTo(o1.getId()));
        return mCars;
    }

    //endregion

}
