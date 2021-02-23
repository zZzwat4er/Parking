package com.example.parking.ui.Vehicle.DataTab;

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
import com.example.parking.ui.Vehicle.VehicleViewModel;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;

import java.util.Calendar;
import java.util.function.Consumer;

public class VehicleDataTariffFragment extends Fragment {

    private TextView type;
    private TextView parkingPlace;
    private TextView payedTill;
    private TextView payment;

    private ConstraintLayout daily;
    private ConstraintLayout monthly;
    private ConstraintLayout owner;
    private ConstraintLayout dontChange;

    private Integer changeStatus = -1;
    private Car currentCar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vehicle_data_tariff, container, false);

        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);

        if(currentCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        type = root.findViewById(R.id.vehicle_data_tariff_current_type_text);
        parkingPlace = root.findViewById(R.id.vehicle_data_tariff_current_place_text);
        payedTill = root.findViewById(R.id.vehicle_data_tariff_current_payed_till_text);
        payment = root.findViewById(R.id.vehicle_data_tariff_current_payment_type_text);

        daily = root.findViewById(R.id.vehicle_data_tariff_change_daily_layout);
        monthly = root.findViewById(R.id.vehicle_data_tariff_change_monthly_layout);
        owner = root.findViewById(R.id.vehicle_data_tariff_change_owner_layout);
        dontChange = root.findViewById(R.id.vehicle_data_tariff_change_dont_change_layout);

        type.setText(currentCar.tariff != null? currentCar.getTariffName() : "");
        if(currentCar.newParkingLotName != null)
            parkingPlace.setText(String.format("%s(%s)",
                currentCar.parkingLotName,
                    currentCar.newParkingLotName != null? currentCar.newParkingLotName : ""));
        else parkingPlace.setText(currentCar.parkingLotName != null? "": currentCar.parkingLotName);
        payedTill.setText(currentCar.payedTill != null? currentCar.getDate() : "");


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
