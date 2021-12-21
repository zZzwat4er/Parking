package com.example.parking.ui.vehicle;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.ui.vehicle.recycler_view.Adapter;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;

public class VehicleFragment extends Fragment {

    private VehicleViewModel viewModel;
    private RecyclerView recyclerView;
    private ConstraintLayout loadinglayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.f_vehicle, container, false);

        viewModel = new ViewModelProvider(this).get(VehicleViewModel.class);
        viewModel.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes outputCodes) {
                switch (outputCodes){
                    case SUC:
                        loadinglayout.setVisibility(View.INVISIBLE);
                        recyclerView.setClickable(true);
                        recyclerView.setAdapter(new Adapter(getActivity(), AccountHolder.account.mCars));
                        break;

                    case ERR:
                        Integer err = viewModel.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;

                    case NONE:
                    default:
                        return;
                }
                viewModel.resetOutPutCode();
            }
        });


        loadinglayout = root.findViewById(R.id.vehicle_progress_bar_layout);
        recyclerView = (RecyclerView) root.findViewById(R.id.vehiacle_rview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new Adapter(getActivity(), AccountHolder.account.mCars));

        if(isNetworkConnected()){
            viewModel.serverRequest(getActivity());
        }

        return root;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
