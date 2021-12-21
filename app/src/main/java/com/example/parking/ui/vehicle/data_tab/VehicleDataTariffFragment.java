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
import com.example.parking.ui.vehicle.VehicleViewModel;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;

public class VehicleDataTariffFragment extends Fragment {

    private TextView type;
    private TextView parkingPlace;
    private TextView payedTill;
    private TextView payment;
    private ConstraintLayout paymentLayout;
    private ConstraintLayout parkingPlaceLayout;

    private ConstraintLayout daily;
    private ConstraintLayout monthly;
    private ConstraintLayout owner;
    private ConstraintLayout dontChange;

    private Integer changeStatus = -1;
    private Car currentCar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.f_vehicle_data_tariff, container, false);

        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);

        if(currentCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        type = root.findViewById(R.id.vehicle_data_tariff_current_type_text);
        parkingPlace = root.findViewById(R.id.vehicle_data_tariff_current_place_text);
        payedTill = root.findViewById(R.id.vehicle_data_tariff_current_payed_till_text);
        payment = root.findViewById(R.id.vehicle_data_tariff_current_payment_type_text);

        paymentLayout = root.findViewById(R.id.vehicle_data_tariff_current_payment_type_layout);
        daily = root.findViewById(R.id.vehicle_data_tariff_change_daily_layout);
        monthly = root.findViewById(R.id.vehicle_data_tariff_change_monthly_layout);
        owner = root.findViewById(R.id.vehicle_data_tariff_change_owner_layout);
        dontChange = root.findViewById(R.id.vehicle_data_tariff_change_dont_change_layout);

        type.setText(currentCar.getTariffName());
        if(currentCar.parkingLotName != null) parkingPlace.setText(currentCar.parkingLotName);
        payedTill.setText(currentCar.getDate());
        //TODO: payment text (ask Stepan about it)

        paymentLayout.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_tariff_to_nav_vehicle_data_tariff_payment));

        updateCheckMarks();


        return root;
    }

    private void updateCheckMarks(){
        daily.getChildAt(1).setVisibility(View.INVISIBLE);
        monthly.getChildAt(1).setVisibility(View.INVISIBLE);
        owner.getChildAt(1).setVisibility(View.INVISIBLE);
        dontChange.getChildAt(1).setVisibility(View.INVISIBLE);
        switch (changeStatus){
            case 0: daily.getChildAt(1).setVisibility(View.VISIBLE); return;
            case 1: monthly.getChildAt(1).setVisibility(View.VISIBLE); return;
            case 2: owner.getChildAt(1).setVisibility(View.VISIBLE); return;
            default: dontChange.getChildAt(1).setVisibility(View.VISIBLE); return;
        }
    }
}
