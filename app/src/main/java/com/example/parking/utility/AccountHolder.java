package com.example.parking.utility;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

public class AccountHolder {
    private static final String accountKey = "account_data";
    private static final String emailKey = "email";
    private static final String passwordHushKey = "password_hash";
    private static SharedPreferences mPrefs;

    public static @Nullable Account account;
    public static @Nullable String email;
    public static @Nullable String passwordHush;

    public static void loadData(Application app){
        if(mPrefs == null) mPrefs = app.getSharedPreferences(
                app.getApplicationInfo().name, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        if(mPrefs.contains(emailKey))
            email = gson.fromJson(mPrefs.getString(emailKey, null), String.class);
        if(mPrefs.contains(passwordHushKey))
            passwordHush = gson.fromJson(
                    mPrefs.getString(passwordHushKey, null), String.class);
        if(mPrefs.contains(accountKey))
            account = gson.fromJson(mPrefs.getString(accountKey, null), Account.class);
        if(account != null) if(account.mEmail == null || account.mFirstName == null || account.mPhone == null){
            account = null;
            passwordHush = null;
            email = null;
            saveData(app);
        }
    }

    public static void saveData(Application app){
        if(mPrefs == null) mPrefs = app.getSharedPreferences(
                app.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();

        Gson gson = new Gson();
        editor.putString(emailKey, gson.toJson(email));
        editor.putString(passwordHushKey, gson.toJson(passwordHush));
        editor.putString(accountKey, gson.toJson(account));
        editor.apply();
    }
}
