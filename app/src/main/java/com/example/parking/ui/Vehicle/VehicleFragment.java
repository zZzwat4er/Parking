package com.example.parking.ui.Vehicle;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parking.R;
import com.example.parking.ui.Vehicle.RecyclerView.Adapter;
import com.example.parking.utility.AccountHolder;

public class VehicleFragment extends Fragment {

    private VehicleViewModel viewModel;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(VehicleViewModel.class);

        View root = inflater.inflate(R.layout.fragment_vehicle, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.vehiacle_rview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new Adapter(getActivity(), AccountHolder.account.mCars));



        return root;
    }
}
