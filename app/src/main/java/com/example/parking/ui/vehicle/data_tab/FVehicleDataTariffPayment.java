package com.example.parking.ui.vehicle.data_tab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;

public class FVehicleDataTariffPayment extends Fragment {

    private ImageView autoContImg;
    private ImageView manualImg;
    private View autoContBtn;
    private View manualBtn;
    private Boolean initState;
    private Boolean curState;
    private Menu mMenu;
    private VehicleDataTariffPaymentVM vm;
    private Car cCar;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cCar = AccountHolder.account.getCarById(VehicleVM.carID);
        if(cCar == null) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

        View root = inflater.inflate(R.layout.f_vehicle_data_tariff_payment, container, false);
        vm = new ViewModelProvider(this).get(VehicleDataTariffPaymentVM.class);
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
                        initState = curState;
                        updateApprove();
                    case NONE:
                    default:
                        break;
                }
            }
        });
        initState = curState = cCar.isAutoCont;
        autoContBtn = root.findViewById(R.id.vehicle_data_tariff_payment_method_auto_layout);
        manualBtn = root.findViewById(R.id.vehicle_data_tariff_payment_method_manual_layout);
        autoContImg = root.findViewById(R.id.vehicle_data_tariff_payment_method_auto_checkmark);
        manualImg = root.findViewById(R.id.vehicle_data_tariff_payment_method_manual_checkmark);

        if(cCar.isAutoCont) autoContImg.setVisibility(View.VISIBLE);
        else manualImg.setVisibility(View.VISIBLE);

        autoContBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curState = true;
                autoContImg.setVisibility(View.VISIBLE);
                manualImg.setVisibility(View.INVISIBLE);
                updateApprove();
            }
        });
        manualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curState = false;
                autoContImg.setVisibility(View.INVISIBLE);
                manualImg.setVisibility(View.VISIBLE);
                updateApprove();
            }
        });

        setHasOptionsMenu(true);
        updateApprove();
        return root;
    }

    private void updateApprove(){
        if(mMenu == null) return;
        if(mMenu.findItem(R.id.approve) == null) return;
        if(initState != curState){
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
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            vm.serverRequest(getActivity(), cCar.id, curState);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
