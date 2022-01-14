package com.example.parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final NavController navController = Navigation.findNavController(this, R.id.login_nav_host);
        navController.setGraph(R.navigation.login_navigation);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                final boolean isStartPage = destination.getId() == navController.getGraph().getStartDestination();
                if(isStartPage) {
                    getSupportActionBar().hide();
                }
                else {
                    getSupportActionBar().show();
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
//        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        NavController navController = Navigation.findNavController(this, R.id.login_nav_host);
        return navController.navigateUp();
    }
}