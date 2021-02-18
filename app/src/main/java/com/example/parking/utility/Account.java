package com.example.parking.utility;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

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
    //endregion

}
