package com.example.parking.ui.Vehicle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.ui.Vehicle.RecyclerView.Adapter;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleFragment extends Fragment {

    private VehicleViewModel viewModel;
    private RecyclerView recyclerView;
    private ConstraintLayout loadinglayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_vehicle, container, false);

        loadinglayout = root.findViewById(R.id.vehicle_progress_bar_layout);
        recyclerView = (RecyclerView) root.findViewById(R.id.vehiacle_rview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new Adapter(getActivity(), AccountHolder.account.mCars));

        if(isNetworkConnected()){
            comAPI.login(AccountHolder.email,
                    AccountHolder.passwordHush,
                    getActivity().getApplicationContext(),
                    new HttpRequest.Listener() {
                        @Override
                        public void onRespond(String respond) {
                            AccountHolder.account = JSONPars.parseAccount(respond);
                            if(AccountHolder.account != null) {
                                loadinglayout.setVisibility(View.INVISIBLE);
                                recyclerView.setClickable(true);
                                AccountHolder.saveData(getActivity().getApplication());
                            }else{
                                ServerError err = JSONPars.parseErrorServer(respond);
                                if(err != null){
                                    if(err.code == 2){
                                        AccountHolder.dataFlesh(getActivity().getApplication());
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                            }
                        }
                    });
        }

        viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);





        return root;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
