package com.example.parking.ui.vehicle.data_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.ui.vehicle.VehicleVM;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;

public class FVehicleDataTariff extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.f_vehicle_data_tariff, container, false);

        Car currentCar = AccountHolder.account.getCarById(VehicleVM.carID);

        if(currentCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        TextView type = root.findViewById(R.id.vehicle_data_tariff_current_type_text);
        TextView parkingPlace = root.findViewById(R.id.vehicle_data_tariff_current_place_text);
        TextView payedTill = root.findViewById(R.id.vehicle_data_tariff_current_payed_till_text);
        TextView payment = root.findViewById(R.id.vehicle_data_tariff_current_payment_type_text);

        ConstraintLayout paymentLayout = root.findViewById(R.id.vehicle_data_tariff_current_payment_type_layout);
        ConstraintLayout parkingPlaceLayout = root.findViewById(R.id.vehicle_data_tariff_current_place_layout);
        ConstraintLayout becomeOwner = root.findViewById(R.id.vehicle_data_tariff_become_owner_btn);

        becomeOwner.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_tariff_to_nav_buy_place));

        type.setText(currentCar.getTariffName());
        if(currentCar.parkingLotName != null) parkingPlace.setText(currentCar.parkingLotName);
        payedTill.setText(currentCar.getDate());
        //TODO: payment text (ask Stepan about it)

        paymentLayout.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_tariff_to_nav_vehicle_data_tariff_payment));
        parkingPlaceLayout.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_tariff_to_nav_vehicle_data_tariff_place));

        return root;
    }
}
