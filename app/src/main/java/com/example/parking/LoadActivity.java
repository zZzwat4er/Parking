package com.example.parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.example.parking.utility.Account;
import com.example.parking.utility.AccountHolder;

public class LoadActivity extends AppCompatActivity {

    private @Nullable Account account;
    private @Nullable String email;
    private @Nullable String passwordHush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        //testing code
        /*AccountHolder.email = "asd";
        AccountHolder.account =  Account.createAccount("email", "somename", new Long(12312));
        AccountHolder.passwordHush = "AccountHolder.passwordHush";
        AccountHolder.saveData(getApplication());

        AccountHolder.email = null;
        AccountHolder.account = null;
        AccountHolder.passwordHush = null;*/

        AccountHolder.loadData(getApplication());
        email = AccountHolder.email;
        account = AccountHolder.account;
        passwordHush = AccountHolder.passwordHush;


        Intent intent;
        if(account != null && email != null && passwordHush != null){
            //todo сверяем инфу с сервером
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}