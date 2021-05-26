package com.example.parking.ui.Vehicle;

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
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleAddFragment extends Fragment {

    private EditText plates;
    private EditText main_cid;
//    private EditText addi_cid;
    private String TAG = "Add Tab";
    private Menu mMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_vehicle_add_tab, container, false);

        plates = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_plates_layout)).getChildAt(0);
        main_cid = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_main_cid_layout)).getChildAt(0);
//        addi_cid = (EditText)((LinearLayout)root.findViewById(
//                R.id.add_vehicle_additional_cid_layout)).getChildAt(0);

        plates.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {updateApprove();}
        });
        main_cid.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {updateApprove();}
        });
//        addi_cid.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override public void afterTextChanged(Editable s) {updateApprove();}
//        });

        setHasOptionsMenu(true);

        updateApprove();

        return root;
    }

    private void updateApprove() {
        if(mMenu == null) return;
        String tempPlates = plates.getText().toString();
        tempPlates = StringChecker.eng2rus(tempPlates);
        if(!main_cid.getText().toString().isEmpty() && !tempPlates.isEmpty())
            if(StringChecker.isCard(main_cid.getText().toString()) &&
//                    StringChecker.isCard(addi_cid.getText().toString()) &&
                    StringChecker.isPlates(tempPlates)){
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
        switch(item.getItemId()){
            case R.id.approve:
                String tempPlates = plates.getText().toString();
                tempPlates = StringChecker.eng2rus(tempPlates);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                if(StringChecker.isCard(main_cid.getText().toString()) &&
//                    StringChecker.isCard(addi_cid.getText().toString()) &&
                    StringChecker.isPlates(tempPlates)){
                    try {
                        comAPI.setCallBack(new comAPI.OnThreadExit() {
                            @Override
                            public void exit() {
                                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                            }
                        });
                        comAPI.addCar(AccountHolder.email,
                                AccountHolder.passwordHush,
                                tempPlates,
                                Integer.parseInt(main_cid.getText().toString()),
//                                Integer.parseInt(addi_cid.getText().toString()), // if returned also check addCar func
                                getActivity().getApplicationContext(),
                                new HttpRequest.Listener() {
                                    @Override
                                    public void onRespond(String respond) {
                                        AccountHolder.account = JSONPars.parseAccount(respond);
                                        if(AccountHolder.account != null) {
                                            AccountHolder.saveData(getActivity().getApplication());
                                        }
                                    }
                                });
                    }catch (Exception e){}
                }

                //todo approve car addition
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}
