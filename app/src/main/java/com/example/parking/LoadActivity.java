package com.example.parking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        hideSystemUI();
        startActivity(intent);
        finish();
    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        newUiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);
    }
}