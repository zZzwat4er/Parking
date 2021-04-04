package com.example.parking.ui.Vehicle.DataTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.ui.Vehicle.VehicleViewModel;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;

public class VehicleDataFragment extends Fragment {

    private ConstraintLayout plates;
    private ConstraintLayout tariff;
    private ConstraintLayout cards;
    private Car currentCar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);

        if(currentCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        View root = inflater.inflate(R.layout.fragment_vehicle_data, container, false);

        plates = root.findViewById(R.id.vehicle_data_plates_layout);
        tariff = root.findViewById(R.id.vehicle_data_tariff_layout);
        cards = root.findViewById(R.id.vehicle_data_card_layout);

        ((EditText)plates.getChildAt(1)).setText(currentCar.plates);

        cards.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_to_nav_vehicle_data_cards));
        tariff.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_to_nav_vehicle_data_tariff));

        if (currentCar.parkingLotName != null)
            ((TextView)tariff.getChildAt(1)).setText(currentCar.getTariffName()
                + " - " + currentCar.parkingLotName);

        setHasOptionsMenu(true);
        return root;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_bar_appr, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.approve:
                //todo approve car addition
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
