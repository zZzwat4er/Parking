package com.example.parking.ui.vehicle.data_tab;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.ui.vehicle.VehicleVM;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.ParkingLot;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;

import java.util.Objects;

public class FVehicleData extends Fragment {

    private EditText platesText;
    private VehicleDataVM vm;
    private VehicleDataDelVM vmDel;
    private VehicleDataCardChangeVM vmCard;
    private ConstraintLayout main;
    private Car currentCar;
    private String initPlates;
    private ConstraintLayout exitApproveLayout;
    private String TAG = "Vehicle data";
    private EditText mainCardText;
    private EditText secondaryCardText;
    private String initMainCard;
    private String initSecondCard;

    private View.OnFocusChangeListener cardsChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus) {
                if(!mainCardText.getText().toString().equals(initMainCard) || !secondaryCardText.getText().toString().equals(initSecondCard)) {
                    Integer mainCardNum = Integer.parseInt(mainCardText.getText().toString());
                    Integer secondCardNum = (!secondaryCardText.getText().toString().isEmpty()) ? Integer.parseInt(secondaryCardText.getText().toString()) : null;
                    vmCard.serverRequest(getActivity(), currentCar.id, mainCardNum, secondCardNum);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentCar = AccountHolder.account.getCarById(VehicleVM.carID);

        if(currentCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        View root = inflater.inflate(R.layout.f_vehicle_data, container, false);
        final ConstraintLayout secondaryCard = root.findViewById(R.id.vehicle_data_second_card_layout);
        final ConstraintLayout tariff = root.findViewById(R.id.vehicle_data_tariff_layout);
        final TextView deleteBtn = root.findViewById(R.id.vehicle_data_delete_button);
        final Button exitApprove = root.findViewById(R.id.ui_exit_approve);
        final Button exitCancel = root.findViewById(R.id.ui_exit_reject);

        mainCardText = root.findViewById(R.id.vehicle_data_main_card_layout_edittext);
        secondaryCardText = root.findViewById(R.id.vehicle_data_second_card_layout_edittext);
        main = root.findViewById(R.id.vehicle_data_layout);
        platesText = root.findViewById(R.id.vehicle_data_plates_layout_edittext);
        exitApproveLayout = root.findViewById(R.id.ui_exit_approve_l);
        vm = new ViewModelProvider(this).get(VehicleDataVM.class);
        vmDel = new ViewModelProvider(this).get(VehicleDataDelVM.class);
        vmCard = new ViewModelProvider(this).get(VehicleDataCardChangeVM.class);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.clearFocus();
            }
        });

        exitApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vmDel.serverRequest(getActivity(), currentCar.id);
            }
        });
        exitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApproveLayout.setVisibility(View.GONE);
            }
        });
        exitApproveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApproveLayout.setVisibility(View.GONE);
            }
        });

        mainCardText.setText(currentCar.mainCard.toString());
        secondaryCardText.setText((currentCar.secondMainCard != null)? currentCar.secondMainCard.toString() : "");
        secondaryCard.setVisibility((currentCar.parkingLotType != null && currentCar.parkingLotType == ParkingLot.DOUBLE_PLACE)? View.VISIBLE: View.GONE);


        initMainCard = mainCardText.getText().toString();
        initSecondCard = secondaryCardText.getText().toString();

        mainCardText.setOnFocusChangeListener(cardsChange);
        secondaryCardText.setOnFocusChangeListener(cardsChange);
        platesText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (!platesText.getText().toString().isEmpty()) {
                        Log.d(TAG, "first if");
                        String pStr = platesText.getText().toString();
                        pStr = pStr.toLowerCase();
                        pStr = StringChecker.eng2rus(pStr);
                        if (!initPlates.equals(pStr)) {
                            Log.d(TAG, "second if");
                            if (!StringChecker.isPlates(pStr)) {
                                Log.d(TAG, "third if");
                                return;
                            }
                            vm.serverRequest(getActivity(), currentCar.id, pStr);
                            return;
                        }
                    }
                }
            }
        });

        platesText.setText(currentCar.plates);
        initPlates = currentCar.plates;

        tariff.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_to_nav_vehicle_data_tariff));
        ((TextView) tariff.getChildAt(1)).setText((currentCar.parkingLotName != null)?
                currentCar.getTariffName() + " - " + currentCar.parkingLotName : "");

        vm.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case ERR:
                        Integer err = vm.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;
                    case SUC:
                        Log.d("OBSERVER", "onChanged: get suc result");
                        currentCar = AccountHolder.account.getCarById(VehicleVM.carID);
                        initPlates = currentCar.plates;
                        platesText.setText(currentCar.plates);
                        main.clearFocus();
                    case NONE:
                    default:
                        break;
                }
            }
        });
        vmDel.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case ERR:
                        Integer err = vm.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;
                    case SUC:
                        Log.d("OBSERVER", "onChanged: get suc result");
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                    case NONE:
                    default:
                        break;
                }
            }
        });
        vmCard.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case ERR:
                        Integer err = vm.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;
                    case SUC:
                        Log.d("OBSERVER", "onChanged: get suc result");
                        currentCar = AccountHolder.account.getCarById(VehicleVM.carID);
                        mainCardText.setText(currentCar.mainCard.toString());
                        secondaryCardText.setText((currentCar.secondMainCard != null)? currentCar.secondMainCard.toString() : "");
                        secondaryCard.setVisibility((currentCar.secondMainCard != null)? View.VISIBLE: View.GONE);
                        initMainCard = mainCardText.getText().toString();
                        initSecondCard = secondaryCardText.getText().toString();
                        main.clearFocus();
                    case NONE:
                    default:
                        break;
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApproveLayout.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
}
