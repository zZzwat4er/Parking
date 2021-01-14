package com.example.parking.utility;

import androidx.annotation.Nullable;

public class Account {
    public String mEmail;
    public Long mPhone;
    public String mFirstName;
    public @Nullable String mSecondName;
    public String mLastName;
    public Integer mBalance;
    public Car[] mCars;

    private Account(String email, @Nullable String firstName, @Nullable String secondName,
                    @Nullable String lastName, @Nullable Long phone,
                    @Nullable Integer balance, @Nullable Car[] cars){
        if(firstName == null || lastName == null || phone == null || balance == null){
            dataResume();
        }else{
            mEmail = email;
            mPhone = phone;
            mFirstName = firstName;
            mSecondName = secondName;
            mLastName = lastName;
            mBalance = balance;
            mCars = cars;
        }
    }

    public Account(String email){this(email, null, null, null, null, null, null);}

    public static Account createAccount(String email, String firstName, String secondName, String lastName, Long phone){
        return new Account(email, firstName, secondName, lastName, phone, 0, null);
    }

    //region Get/Set/Edit func
    public String getFullName(){
        return mLastName + " " + mFirstName + ((mSecondName != null)? " " + mSecondName : "");
    }
    public String getPhoneString(){
        String s = "+7" + mPhone.toString();
        return s.replaceFirst("(\\+7)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "$1 ($2) $3-$4-$5");
    }
    //endregion

    private void dataResume(){
        //todo: http запрос на докачку данных
    }

}
