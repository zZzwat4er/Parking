package com.example.parking.ui.vehicle.data_tab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.ui.vehicle.VehicleVM;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;
import com.example.parking.utility.MapTouchListener;
import com.example.parking.utility.server_comunnication_api.ParkingLot;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;

public class FVehicleDataTariffPlace extends Fragment {

    private Car cCar;
    private View choosePlace;
    private TextView place;
    private ImageView map;
    private NumberPicker wheelSelector;
    private Menu mMenu;
    private Integer initState;
    private Integer curState;
    private VehicleDataTariffPlaceVM vm;
    private VehicleDataTariffPlaceLotVM vmLot;
    private ConstraintLayout progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cCar = AccountHolder.account.getCarById(VehicleVM.carID);
        if(cCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        vm = new ViewModelProvider(this).get(VehicleDataTariffPlaceVM.class);
        vmLot = new ViewModelProvider(this).get(VehicleDataTariffPlaceLotVM.class);
        initState = curState = 0;

        final View root = inflater.inflate(R.layout.f_vehicle_data_tariff_place, container, false);
        map = root.findViewById(R.id.tariff_place_map);
        place = root.findViewById(R.id.tariff_place_current_place);
        choosePlace = root.findViewById(R.id.tariff_place_current_layout);
        wheelSelector = root.findViewById(R.id.tariff_place_wheel_selector);
        progressBar = root.findViewById(R.id.tariff_place_progress_bar);
        //TODO: init wheelSelector

        place.setText((cCar.parkingLotName != null)? cCar.parkingLotName : getString(R.string.no_place));

        final MapTouchListener listener = new MapTouchListener();
        map.setOnTouchListener(listener);

        root.findViewById(R.id.zoom_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouch(map, MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0));
                listener.zoomIn();
                listener.onTouch(map, MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0));
            }
        });
        root.findViewById(R.id.zoom_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouch(map, MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0));
                listener.zoomOut();
                listener.onTouch(map, MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0));
            }
        });

        choosePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wheelSelector.getVisibility() == View.VISIBLE) wheelSelector.setVisibility(View.GONE);
                else wheelSelector.setVisibility(View.VISIBLE);
            }
        });
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.clearFocus();
            }
        });
        wheelSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("valueChanged", String.format("%s", newVal));
                curState = newVal;
                updateApprove();
            }
        });

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
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigateUp();
                        //TODO: navigate to tariff payment;
                        break;
                    case NONE:
                    default:
                        break;
                }
            }
        });
        vmLot.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case ERR:
                        Integer err = vm.getErrorCode();
                        if(err != null && err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;
                    case SUC:
                        progressBar.setVisibility(View.GONE);
                        ParkingLot[] lots = vmLot.getLots();
                        String[] data = new String[lots.length + 1];
                        data[0] = cCar.parkingLotName != null? String.format("%s - (текущее)", cCar.parkingLotName) : getString(R.string.no_place);
                        for(int i = 0; i < lots.length; i++){
                            data[i + 1] = lots[i].toString();
                        }
                        wheelSelector.setMinValue(0);
                        wheelSelector.setMaxValue(lots.length);
                        wheelSelector.setDisplayedValues(data);
                        break;
                    case NONE:
                    default:
                        break;
                }
            }
        });

        vmLot.serverRequest(getActivity());
        setHasOptionsMenu(true);
        updateApprove();

        return root;
    }

    private void updateApprove(){
        if(mMenu == null) return;
        if(mMenu.findItem(R.id.approve) == null) return;
        if(!initState.equals(curState)){
            mMenu.findItem(R.id.approve).setEnabled(true);
            return;
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
        if (item.getItemId() == R.id.approve) {
            vm.serverRequest(getActivity(), cCar.id, vmLot.getLots()[curState - 1].id);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
