package com.example.parking.ui.Vehicle.DataTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.ui.Vehicle.VehicleViewModel;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.Car;

public class VehicleDataCardsFragment extends Fragment {

    private Car currentCar;

    private TextView[] cards = new TextView[7];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentCar = AccountHolder.account.getCarById(VehicleViewModel.carID);

        View root = inflater.inflate(R.layout.fragment_vehicle_data_cards, container, false);

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

        return root;
    }
}
