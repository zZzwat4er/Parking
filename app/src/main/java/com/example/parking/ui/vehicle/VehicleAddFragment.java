package com.example.parking.ui.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;

public class VehicleAddFragment extends Fragment {

    private EditText plates;
    private EditText main_cid;
    private String TAG = "Add Tab";
    private Menu mMenu;
    VehicleAddViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_vehicle_add_tab, container, false);

        viewModel = new ViewModelProvider(this).get(VehicleAddViewModel.class);

        plates = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_plates_layout)).getChildAt(0);
        main_cid = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_main_cid_layout)).getChildAt(0);

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

        viewModel.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case SUC:
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigateUp();
                        if(!viewModel.isFromVehicleTab)navController.navigate(R.id.action_nav_map_to_nav_vehicle);
                        break;

                    case ERR:
                        Integer err = viewModel.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;

                    case NONE:
                    default:
                        return;
                }
                viewModel.resetOutPutCode();
            }
        });

        //                    try {
//                        comAPI.setCallBack(new comAPI.OnThreadExit() {
//                            @Override
//                            public void exit() {
//                                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
//                            }
//                        });
//                        comAPI.addCar(AccountHolder.email,
//                                AccountHolder.passwordHush,
//                                tempPlates,
//                                Integer.parseInt(main_cid.getText().toString()),
////                                Integer.parseInt(addi_cid.getText().toString()), // if returned also check addCar func
//                                getActivity().getApplicationContext(),
//                                new HttpRequest.Listener() {
//                                    @Override
//                                    public void onRespond(String respond) {
//                                        AccountHolder.account = JSONPars.parseAccount(respond);
//                                        if(AccountHolder.account != null) {
//                                            AccountHolder.saveData(getActivity().getApplication());
//                                        }
//                                    }
//                                });
//                    }catch (Exception e){}
//                }


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
                    StringChecker.isPlates(tempPlates)){
                    viewModel.serverRequest(getActivity(), tempPlates, main_cid.getText().toString());
                }
                //todo approve car addition
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}
