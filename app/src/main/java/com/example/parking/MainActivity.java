package com.example.parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.ParkingLot;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;
import com.example.parking.utility.server_comunnication_api.comAPI;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static TextView hFio;
    private static TextView hPhone;
    private final Context context = this;
    private HttpVM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(HttpVM.class);
        viewModel.getOutPutCode().observe(this, new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                Log.d("OBSERVER", "onChanged");
                switch (serverReqCodes){
                    case ERR:
                        Integer err = viewModel.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getApplication());
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case SUC:
                        Log.d("OBSERVER", "onChanged: get suc result");
                    case NONE:
                    default:
                        break;
                }
            }
        });

        if(getIntent().getStringExtra("S").equals("load") && isNetworkConnected()){
            viewModel.serverRequest(this);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                /*R.id.nav_user_info,
                R.id.nav_top_up_an_account,
                R.id.nav_history,
                R.id.nav_notification,
                R.id.nav_vehicle,
                R.id.nav_rate_plan,
                R.id.nav_buy_place,
                R.id.nav_qna,
                R.id.nav_about*/)
                .setDrawerLayout(drawer).build();



        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navController.getCurrentDestination().getId() == navController.getGraph().getStartDestination())
                    navController.navigate(R.id.action_nav_map_to_nav_user_info);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        navController.setGraph(R.navigation.mobile_navigation);

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

        // setting data
        hFio = header.findViewById(R.id.header_fio);
        hPhone = header.findViewById(R.id.header_phone);
        hFio.setText(AccountHolder.account.getFullName());
        hPhone.setText(AccountHolder.account.mPhone);
    }

    public static void notifyDataChanged(){
        hFio.setText(AccountHolder.account.getFullName());
        hPhone.setText(AccountHolder.account.mPhone);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController;
        try {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        }catch (NullPointerException e){
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        }
        return navController.navigateUp();
    }

    public void menuButton(View view){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.LEFT);
    }

    public void testFunc(View v){
        comAPI.getAvailableParkingLots(this,
                new HttpRequest.Listener() {
                    @Override
                    public void onRespond(String respond) {
                        Log.d("respond", respond);
                        ParkingLot[] lots = JSONPars.parseParkingLots(respond);
                        Log.d("lots", (lots == null)? "null" : "nonnull");
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}