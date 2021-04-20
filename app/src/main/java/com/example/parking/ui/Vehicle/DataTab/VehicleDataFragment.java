package com.example.parking.ui.Vehicle.DataTab;

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
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleDataFragment extends Fragment {

    private ConstraintLayout plates;
    private EditText platesText;
    private ConstraintLayout tariff;
    private ConstraintLayout cards;
    private TextView deleteBtn;
    private Car currentCar;
    private Menu mMenu;
    private String initPlates;
    private String TAG = "Vehicle data";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);

        if(currentCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        View root = inflater.inflate(R.layout.fragment_vehicle_data, container, false);

        plates = root.findViewById(R.id.vehicle_data_plates_layout);
        platesText = root.findViewById(R.id.vehicle_data_plates_layout_edittext);
        tariff = root.findViewById(R.id.vehicle_data_tariff_layout);
        cards = root.findViewById(R.id.vehicle_data_card_layout);
        deleteBtn = root.findViewById(R.id.vehicle_data_delete_button);

        platesText.setText(currentCar.plates);
        initPlates = currentCar.plates;
        platesText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {updateApprove();}
        });

        cards.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_to_nav_vehicle_data_cards));
        tariff.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_vehicle_data_to_nav_vehicle_data_tariff));

        if (currentCar.parkingLotName != null)
            ((TextView)tariff.getChildAt(1)).setText(currentCar.getTariffName()
                + " - " + currentCar.parkingLotName);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comAPI.deleteCar(
                        AccountHolder.email,
                        AccountHolder.passwordHush,
                        currentCar.id,
                        getActivity().getApplicationContext(),
                        new HttpRequest.Listener() {
                            @Override
                            public void onRespond(String respond) {
                                
                            }
                        }
                );
            }
        });

        setHasOptionsMenu(true);

        updateApprove();

        return root;
    }

    private void updateApprove(){
        if(mMenu == null) return;
        if(mMenu.findItem(R.id.approve) == null) return;
        if(!platesText.getText().toString().isEmpty()){
            Log.d(TAG, "first if");
            String pStr = platesText.getText().toString();
            pStr = pStr.toLowerCase();
            pStr = StringChecker.eng2rus(pStr);
            if(!initPlates.equals(pStr)){
                Log.d(TAG, "second if");
                if(!StringChecker.isPlates(pStr)){
                    Log.d(TAG, "third if");
                    mMenu.findItem(R.id.approve).setEnabled(false);
                    return;
                }
                mMenu.findItem(R.id.approve).setEnabled(true);
                return;
            }
        }
        mMenu.findItem(R.id.approve).setEnabled(false);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_bar_appr, menu);
        mMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.approve:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                String pStr = platesText.getText().toString();
                pStr = pStr.toLowerCase();
                pStr = StringChecker.eng2rus(pStr);
                if(StringChecker.isPlates(pStr)){
                    comAPI.setCallBack(new comAPI.OnThreadExit() {
                        @Override
                        public void exit() {
                            platesText.setText(currentCar.plates);
                            platesText.clearFocus();
                            updateApprove();
                        }
                    });
                    comAPI.updatePlates(
                            AccountHolder.email,
                            AccountHolder.passwordHush,
                            currentCar.id,
                            pStr,
                            getActivity().getApplicationContext(),
                            new HttpRequest.Listener() {
                                @Override
                                public void onRespond(String respond) {
                                    AccountHolder.account = JSONPars.parseAccount(respond);
                                    if(AccountHolder.account != null){
                                        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);
                                        initPlates = currentCar.plates;
                                    }
                                }
                            }
                    );
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
