package com.example.parking.ui.Vehicle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parking.R;

public class VehicleAddFragment extends Fragment {

    private EditText plates;
    private EditText main_cid;
    private EditText addi_cid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_vehicle_add_tab, container, false);

        plates = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_plates_layout)).getChildAt(0);
        main_cid = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_main_cid_layout)).getChildAt(0);
        addi_cid = (EditText)((LinearLayout)root.findViewById(
                R.id.add_vehicle_additional_cid_layout)).getChildAt(0);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_bar_appr, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.approve:
                //todo approve car addition
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}
