package com.example.parking.ui.Vehicle.DataTab;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.ui.Vehicle.VehicleViewModel;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;
import com.example.parking.utility.ServerError;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

public class VehicleDataCardsFragment extends Fragment {

    private Car currentCar;

    private TextView[] cards = new TextView[7];
    private String[] initialStrings = new String[7];
    View root;
    Menu mMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);

        root = inflater.inflate(R.layout.fragment_vehicle_data_cards, container, false);

        cards[0] = root.findViewById(R.id.vehicle_data_cards_main1_text);
        cards[1] = root.findViewById(R.id.vehicle_data_cards_main2_text);
        cards[2] = root.findViewById(R.id.vehicle_data_cards_additional1_text);
        cards[3] = root.findViewById(R.id.vehicle_data_cards_additional2_text);
        cards[4] = root.findViewById(R.id.vehicle_data_cards_additional3_text);
        cards[5] = root.findViewById(R.id.vehicle_data_cards_additional4_text);
        cards[6] = root.findViewById(R.id.vehicle_data_cards_additional5_text);

        cards[0].setText(currentCar.mainCard.toString());
        cards[1].setText((currentCar.secondMainCard != null)? currentCar.secondMainCard.toString() : "");
        for(int i = 2; i < cards.length; i++){
            cards[i].setText((currentCar.additionalCards[i-2] != null)? currentCar.additionalCards[i-2].toString() : "");
        }

        for(int i = 0; i < cards.length; i++) {
            initialStrings[i] = cards[i].getText().toString();
            cards[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    updateApprove();
                }
            });
        }

        setHasOptionsMenu(true);

        updateApprove();

        return root;
    }

    private void updateApprove(){
        if(mMenu == null) return;
        for(int i = 0; i < cards.length; i++) {
            if(!initialStrings[i].equals(cards[i].getText().toString())){
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
        if(item.getItemId() == R.id.approve){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            if(!cards[0].getText().toString().isEmpty() && !cards[2].getText().toString().isEmpty()) {
                comAPI.setCallBack(new comAPI.OnThreadExit() {
                    @Override
                    public void exit() {
                        Navigation.findNavController(root).navigateUp();
                    }
                });
                comAPI.updateCards(AccountHolder.email, AccountHolder.passwordHush, currentCar.id,
                        Integer.parseInt(cards[0].getText().toString()),
                        cards[1].getText().toString().isEmpty() ? null : Integer.parseInt(cards[1].getText().toString()),
                        Integer.parseInt(cards[2].getText().toString()),
                        cards[3].getText().toString().isEmpty() ? null : Integer.parseInt(cards[3].getText().toString()),
                        cards[4].getText().toString().isEmpty() ? null : Integer.parseInt(cards[4].getText().toString()),
                        cards[5].getText().toString().isEmpty() ? null : Integer.parseInt(cards[5].getText().toString()),
                        cards[6].getText().toString().isEmpty() ? null : Integer.parseInt(cards[6].getText().toString()),
                        getActivity().getApplicationContext(),
                        new HttpRequest.Listener() {
                            @Override
                            public void onRespond(String respond) {
                                AccountHolder.account = JSONPars.parseAccount(respond);
                                if (AccountHolder.account != null) {
                                    AccountHolder.saveData(getActivity().getApplication());
                                    //Navigation.findNavController(root).navigateUp();
                                } else {
                                    ServerError err = JSONPars.parseErrorServer(respond);
                                    if (err != null) {
                                        if (err.code == 2) {
                                            AccountHolder.dataFlesh(getActivity().getApplication());
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    }
                                }
                            }
                        }
                );
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
