package com.example.parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.example.parking.utility.Account;
import com.example.parking.utility.AccountHolder;

public class LoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        AccountHolder.loadData(getApplication());

        String email = AccountHolder.email;
        Account account = AccountHolder.account;
        String passwordHush = AccountHolder.passwordHush;

        Intent intent;
        if(account != null && email != null && passwordHush != null){
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("S", "load");
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}